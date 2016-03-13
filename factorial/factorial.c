
int factorial(int x) {
	int i, result = 1;
	if(x == 0)
		return 1;
	bool negative = x < 0;
	i = x;
	if(negative) {
		i = -1 * x;
	}
	for(i; i > 1; i-=1) {
		result = result * i;
	}
	if(negative) {
		return result * -1;
	}
	return result;
}
	