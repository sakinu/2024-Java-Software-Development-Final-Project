#include<iostream>
#include<string>
#include<stdlib.h>
#include<iomanip>
#include<vector>

using namespace std;

int main(int argc, char **argv) {
    int x = 0;
    cout << ( x) << endl;
    x = 10;
    cout << ( x) << endl;
    x += 2;
    cout << ( x) << endl;
    x -= 3;
    cout << ( x) << endl;
    x *= 4;
    cout << ( x) << endl;
    x /= 5;
    cout << ( x) << endl;
    x %= 6;
    cout << ( x) << endl;
    x |= 4092;
    cout << ( x) << endl;
    x &= 255;
    cout << ( x) << endl;

    float yy = 3.14;
    cout << ( yy ) << endl;
    yy = 10.4;
    cout << ( yy ) << endl;
    yy += 2.0;
    cout << ( yy ) << endl;
    yy -= 3.0;
    cout << ( yy ) << endl;
    yy *= 4.0;
    cout << ( yy ) << endl;
    yy /= 5.0;
    cout << ( yy ) << endl;

    string s = "";
    cout << ( s) << endl;
    s = "Hello";
    cout << ( s) << endl;

    bool bbb = false;
    cout << ( bbb) << endl;
    bbb = true;
    cout << ( bbb) << endl;
}

/*
0
10
12
9
36
7
1
4093
253
3.14
10.4
12.4
9.4
37.6
7.52

Hello
0
1
*/