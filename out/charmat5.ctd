   a[0] = 65;
   a[1] = 66;
   a[2] = 67;
   i = 0;
L0:
   if (i < 3) goto L2;
   goto L3;
L1:
   t0 = i + 1;
   i = t0;
   goto L0;
L2:
# Comprobacion de rango
   if (i < 0) goto L5;
   if (3 < i) goto L5;
   if (3 == i) goto L5;
   goto L4;
L5:
   error;
   halt;
L4:
   t1 = a[i];
   printc t1;
   goto L1;
L3:
   print 3;
