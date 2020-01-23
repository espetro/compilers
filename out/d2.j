.method public static polinomio(I)I
    sipush 1
    istore 1
    sipush 1
    sipush 1
    iadd
    istore 2
    sipush 1
    sipush 1
    iadd
    sipush 1
    iadd
    istore 3
    iload 1
    iload 0
    imul
    iload 0
    imul
    iload 2
    iload 0
    imul
    iadd
    iload 3
    iadd
    istore 4
    iload 4
    ireturn
    nop
    .limit stack 10
    .limit locals 5
.end method

.method public static main(I)I
    iload 0
    invokestatic JPL/polinomio(I)I
    iload 0
    sipush 1
    iadd
    invokestatic JPL/polinomio(I)I
    iadd
    ireturn
    nop
    .limit stack 10
    .limit locals 1
.end method

