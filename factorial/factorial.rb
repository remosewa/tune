def factorial(x)
    return 1 if x == 0;
    isNeg = x < 0
    result = x.abs * factorial(x.abs-1)
    return -1 * result if isNeg
    return result;
end