#include <iostream>
#include "FIFOQueue.h"
using namespace std;

FIFOQueue::FIFOQueue() {
}

void FIFOQueue::Enqueue(int number) {
    static int counter = 1;
    data[counter] = number;
    counter++;
}

int FIFOQueue::Dequeue() {
    int key = data.size();
    int to_return = data[key];
    data.erase(data[key]);
    return to_return;
}

bool FIFOQueue::isEmpty() {
    if (data.empty()) {
        return true;
    } else {
        return false;
    }
}
