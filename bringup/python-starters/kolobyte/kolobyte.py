#!/usr/bin/env python
""" The Kolobyte Easier Python Library
"""
import hashlib

def md5(s):
    return hashlib.md5(s).hexdigest()
