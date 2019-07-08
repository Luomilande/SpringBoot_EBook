int main()
{
    int a;
	int b;
	int c;
	cin >> a >> b >> c;
	if (a*b*c != 0 && (a + b > c&&b + c > a&&a + c > b))
	{
		if (a == b&&b == c)
		{
			cout << "您输入的是等边三角形！";
		}
		else if ((a == b) || (b == c) || (a == c))
		{
			cout << "您输入的是等腰三角形！";
		}
		else if ((a*a + b*b == c*c) || (b*b + c*c == a*a) || (a*a + c*c == b*b))
		{
			cout << "您输入的是直角三角形！";
		}
		else {
			cout << "普通三角形";
		}
	}
	else{
		cout << "您输入的不能构成三角形";
	}
}