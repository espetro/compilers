.method public static main(I)I
    sipush 0
    istore 1
    sipush 0
    istore 1
    sipush 0
    istore 2
L0:
    iload 0
    iload 2
    isub
    ifgt L1
    goto L2
L1:
    sipush 0
    istore 3
    sipush 0
    istore 4
L4:
    iload 0
    iload 3
    isub
    ifgt L5
    goto L6
L5:
L8:
    iload 0
    iload 4
    isub
    ifgt L9
    goto L10
L9:
    iload 3
    sipush 1
    iadd
    istore 3
    iload 4
    sipush 1
    iadd
    istore 4
    iload 1
    iload 2
    iadd
    iload 3
    iadd
    iload 4
    iadd
    istore 1
    goto L8
L10:
    goto L4
L6:
    iload 2
    sipush 1
    iadd
    istore 2
    goto L0
L2:
    iload 1
    ireturn
    nop
    .limit stack 10
    .limit locals 5
.end method

