#pragma once
#include <map>

using namespace std;

class FIFOQueue {
public:
    /**
    * Create an empty Queue. */
    FIFOQueue();

    /**
    * Enqueues an integer into the queue. */
    void Enqueue(int number);

    /**
    * Dequeues the head number from the queue. */
    int Dequeue();

    /**
    * Returns true iff the queue is currently empty.
    */
    bool isEmpty();

private:
    map<int, int> data; /** Records the queue's data. */
};

