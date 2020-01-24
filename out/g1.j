.method public static main(I)I
    sipush 0
    istore 1
    sipush 0
    istore 1
L0:
    iload 1
    iload 0
    isub
    ifgt L1
    goto L2
L1:
    iload 1
    sipush 1
    iadd
    istore 1
    goto L0
L2:
    iload 1
    ireturn
    nop
    .limit stack 10
    .limit locals 2
.end method

