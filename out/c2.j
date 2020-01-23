.method public static main(I)I
    sipush 1
    istore 1
    sipush 1
    sipush 1
    iadd
    istore 2
    sipush 3
    istore 3
    sipush 0
    istore 4
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
    iload 4
    iadd
    ireturn
    nop
    .limit stack 10
    .limit locals 5
.end method

