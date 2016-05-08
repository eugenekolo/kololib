#!/usr/bin/env python
"""The Kolobyte Python Library
"""
import hashlib
import math
import os
import time

def md5(s):
    """Simpler md5sum a string
    """
    return hashlib.md5(s).hexdigest()

def isPrime(n):
    """Simple, and slow check if a number is prime or not
    """
    if n == 2 or n == 3:
        return True
    if n < 2 or n % 2 == 0:
        return False # 1, negative or even
    for i in xrange(3, int(math.sqrt(n))+1, 2):
        if n % i == 0:
            return False
    return True

def num(s):
    """Convert a string to an integer or float
    """
    try:
        return int(s)
    except ValueError:
        return float(s)

def rotn(s, n, letters="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"):
    """Rotate a string's characters by n
    """
    ans = ""
    for c in s:
        if not c.isalpha():
            ans += c
            continue
        ans += letters[(letters.index(c) + n) % len(letters)]
    return ans

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

def connect_pop3(mailserver, username, password):
    """Connect to a pop3 mailserver and return a handle to it
    """
    import poplib
    p = poplib.POP3(mailserver)
    p.user(username)
    p.pass_(password)

    return p


def dns_a_record(host):
    """Returns the DNS A-record of a host
    """
    try:
        import dns.resolver as dns
    except ImportError as e:
        print("[ERROR] Try pip install dnspython, or https://github.com/rthalley/dnspython")
        raise

    return dns.query(host,"A")

def send_smtp(mailserver, port, fromaddr, toaddr, subject, body):
    """Sends a message consisting of a `subject`, and `body`, from a `fromaddr`, to a `toaddr`.
    Spoofing is possible. Port must be a number.
    """
    import smtplib

    fromheader = 'From: %s\r\n' % fromaddr
    toheader = 'To: %s\r\n\r\n' % toaddr

    msg = '%s\n%s\n%s\n\n%s' % (fromheader, toheader, subject, body)

    s = smtplib.SMTP(mailserver, port)
    s.sendmail(fromaddr, toaddr, msg)
    s.quit()


def fork_exec(prog, args):
    """Lower level subprocess.Popen(). Uses fork() and execv() instead.
    Prog must be a string, and args must be a list.
    """
    pid = os.fork() # Returns 0 in the child, and the real pid in the parent
    if pid == 0:
        os.execv(prog, args)
    else:
        pass

def strxor(str1, str2):
    """Xors 2 strings character by character.
    """
    minlen = min(len(str1), len(str2))
    ans = ""
    for (c1, c2) in zip(str1[:minlen], str2[:minlen]):
        ans += chr(ord(c1) ^ ord(c2))
    return ans

class Singleton:
    """A non-thread-safe helper class to ease implementing singletons.
    This should be used as a decorator.

    The decorated class can define one `__init__` function that
    takes only the `self` argument. Other than that, there are
    no restrictions that apply to the decorated class.

    Limitations: The decorated class cannot be inherited from.

    Example:
    @Singleton
    class SharedSecretHolder:
        secret = "Foo"
        def __init__(self):
            print("SharedSecretHolder Singleton initalized!")
            self.secret = "It's a secret!"

    secret_holder1 = SharedSecretHolder.Instance()
    secret_holder2 = SharedSecretHolder.Instance()
    print(secret_holder2.secret)
    secret_holder1.secret = "The secret changed, but the other instance picked it up!"
    print(secret_holder2.secret)
    """
    def __init__(self, decorated):
        self._decorated = decorated

    def Instance(self):
        """Use to get the single instance of the Singleton. Upon first use, it calls the singleton's
        init function.
        """
        try:
            return self._instance
        except AttributeError:
            self._instance = self._decorated()
            return self._instance

    def __call__(self):
        raise TypeError('Singletons must be accessed through `Instance()`.')

    def __instancecheck__(self, inst):
        return isinstance(inst, self._decorated)
