.method public static main(I)I
    sipush 0
    istore 1
    
    sipush 0
    istore 2
    
    sipush 0
    istore 3
    
    sipush 1
    istore 1
    
    sipush 2
    istore 2
    
    sipush 3
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
    ireturn
    nop
    .limit stack 10
    .limit locals 10
.end method
