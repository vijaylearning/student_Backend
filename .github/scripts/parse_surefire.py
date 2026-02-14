#!/usr/bin/env python3
"""
Parser for Maven Surefire XML reports to produce a grade.json file.
Supports two scoring modes:
 - simple percent (default): score = round(100 * passed / total)
 - weighted (if .github/grading/weights.json exists): awards points per test class or per-test.

Weights manifest supports keys in two forms:
 - Full class name: "com.example.MyTestClass": points (awarded if all tests in class pass)
 - Specific test: "com.example.MyTestClass#testMethod": points (awarded if that testcase passes)

Scoring rules (mixed allowed):
 - Sum up total points from the manifest.
 - For class-keys: award full points if the class is present in reports and none of its testcases failed/errored.
 - For per-test keys: award points if the specific testcase is present and passed (no failure/error/skipped).

Outputs JSON with: total_tests, passed_tests, failed_tests, skipped_tests, score (0-100), passedAll (bool), breakdown (per-key points)
"""
import sys
import os
import json
import xml.etree.ElementTree as ET
from collections import defaultdict


def parse_reports(dir_path):
    suites = []
    if not os.path.isdir(dir_path):
        return suites

    for fname in os.listdir(dir_path):
        if not fname.endswith('.xml'):
            continue
        path = os.path.join(dir_path, fname)
        try:
            tree = ET.parse(path)
            root = tree.getroot()
            if root.tag == 'testsuites':
                for ts in root.findall('testsuite'):
                    suites.append(ts)
            elif root.tag == 'testsuite':
                suites.append(root)
        except Exception as e:
            print(f"Warning: failed to parse {path}: {e}")
    return suites


def collect_test_results(suites):
    totals = {'tests': 0, 'failures': 0, 'errors': 0, 'skipped': 0}
    # Map: classname -> list of testcase elements
    class_cases = defaultdict(list)
    # Map: (classname, method) -> testcase element
    testcase_map = {}

    for ts in suites:
        for tc in ts.findall('testcase'):
            totals['tests'] += 1
            classname = tc.attrib.get('classname') or tc.attrib.get('class') or 'Unknown'
            method = tc.attrib.get('name') or tc.attrib.get('method') or 'unknown'
            class_cases[classname].append(tc)
            testcase_map[(classname, method)] = tc
            if tc.find('failure') is not None:
                totals['failures'] += 1
            if tc.find('error') is not None:
                totals['errors'] += 1
            if tc.find('skipped') is not None:
                totals['skipped'] += 1
    return totals, class_cases, testcase_map


def load_weights(manifest_path):
    if not os.path.isfile(manifest_path):
        return None
    try:
        with open(manifest_path, 'r') as f:
            data = json.load(f)
            # Expected format: {"com.example.TestClass": points, "com.example.TestClass#testMethod": points, ...}
            return data
    except Exception as e:
        print(f"Warning: failed to read weights manifest {manifest_path}: {e}")
        return None


def compute_weighted_score_from_weights(class_cases, testcase_map, weights):
    breakdown = {}
    total_points = 0
    earned_points = 0

    for key, points in weights.items():
        total_points += points
        if '#' in key:
            # per-test weight
            cls, method = key.split('#', 1)
            tc = testcase_map.get((cls, method))
            if tc is None:
                breakdown[key] = {'points': points, 'earned': 0, 'present': False}
                continue
            failed = tc.find('failure') is not None or tc.find('error') is not None
            skipped = tc.find('skipped') is not None
            earned = points if (not failed and not skipped) else 0
            breakdown[key] = {'points': points, 'earned': earned, 'present': True, 'classname': cls, 'method': method}
            earned_points += earned
        else:
            # class-level weight
            cases = class_cases.get(key, [])
            if len(cases) == 0:
                breakdown[key] = {'points': points, 'earned': 0, 'present': False}
                continue
            failed = any((c.find('failure') is not None or c.find('error') is not None) for c in cases)
            # Note: skipped tests don't disqualify class-level success; change if you prefer
            earned = points if not failed else 0
            breakdown[key] = {'points': points, 'earned': earned, 'present': True, 'tests': len(cases)}
            earned_points += earned

    score = 0
    if total_points > 0:
        score = round(100.0 * earned_points / total_points)
    return score, breakdown, total_points, earned_points


def main():
    reports_dir = sys.argv[1] if len(sys.argv) > 1 else 'target/surefire-reports'
    suites = parse_reports(reports_dir)
    totals, class_cases, testcase_map = collect_test_results(suites)

    tests = totals['tests']
    failures = totals['failures']
    errors = totals['errors']
    skipped = totals['skipped']
    passed = tests - failures - errors - skipped

    weights_manifest = os.path.join('.github', 'grading', 'weights.json')
    weights = load_weights(weights_manifest)

    result = {
        'total_tests': tests,
        'passed_tests': passed if passed >= 0 else 0,
        'failed_tests': failures + errors,
        'skipped_tests': skipped,
        'passedAll': (failures + errors) == 0 and tests > 0,
    }

    if weights:
        score, breakdown, total_points, earned_points = compute_weighted_score_from_weights(class_cases, testcase_map, weights)
        result.update({
            'score': score,
            'grading_mode': 'weighted',
            'total_points': total_points,
            'earned_points': earned_points,
            'breakdown': breakdown
        })
    else:
        score = 0
        if tests > 0:
            score = round(100.0 * passed / tests)
        result.update({
            'score': score,
            'grading_mode': 'simple_percent'
        })

    with open('grade.json', 'w') as f:
        json.dump(result, f, indent=2)

    print(json.dumps(result, indent=2))


if __name__ == '__main__':
    main()
