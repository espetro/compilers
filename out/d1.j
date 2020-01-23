.method public static polinomio(I)I
    iload 0
    sipush 1
    imul
    iload 0
    imul
    iload 0
    sipush 2
    imul
    iadd
    sipush 3
    iadd
    ireturn
    nop
    .limit stack 10
    .limit locals 1
.end method

.method public static main(I)I
    iload 0
    invokestatic JPL/polinomio(I)I
    ireturn
    nop
    .limit stack 10
    .limit locals 1
.end method

