#!/usr/bin/env python
"""The Kolobyte Python Library
"""
import hashlib
import math

def md5(s):
    """Simpler md5sum a string
    """
    return hashlib.md5(s).hexdigest()

def isPrime(n):
    """Simple check if a number is prime or not
    """
    if n == 2 or n == 3: return True # 2 or 3
    if n < 2 or n%2 == 0: return False # 1, negative or even
    for i in range(3,int(math.sqrt(n))+1,2):
        if n%i == 0:
            return False
    return True

def num(s):
    """Convert a string to an integer or float
    """
    try:
        return int(s)
    except ValueError:
        return float(s)


def rotn(s, n):
    """Rotate a string's characters by n
    """
    letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
    ans = ""
    for c in s:
        if not c.isalpha():
            ans += c
            continue
        ans += letters[(letters.index(c) + n) % 52]
    print ans

class PrimeSieve():
    """Sieve of Eratosthenes, finds prime #s up to n in O(nloglogn)
    Usage: primeSieve = PrimeSieve(500)
           primeSieve.primes[25] = 233
    """
    def __init__(self, n):
        """Init a sieve of primes up to `n`
        """
        # Assume [0,n) are all primes
        primes = [True for i in range(0,n)]
        for i in range(2,int(math.ceil(math.sqrt(n)))):
            if primes[i] is True:
                a = 0
                while (i**2 + a*i < n): # Remove every multiple of i
                    primes[i**2 + a*i] = False
                    a += 1

        self.primes = [i for i in range(2,n) if primes[i] is True]
