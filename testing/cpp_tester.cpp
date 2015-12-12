#include <string>
#include <map>
#include <cstdio>
#include <iostream>

int test_func1(int /*foo*/, int /*bar*/) {
	return 0;
}

class Foo {
  public:
	Foo() { 
		printf("Constructor\n"); 
	}
  public:
	void mFunc1(char* foo, size_t len) {
		std::string bar(foo, len);
		std::cout << bar << std::endl;
	}
};

int main(void){
  printf("Hello world!\n");
  Foo foo; char* bar = "Hello";
  foo.mFunc1(bar, 6);
  return 0;
}