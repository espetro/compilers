int m[3];
char st[3];
int i, x, y;
for(i = 0; i < m.length; i = i + 1) {
   m[i] = i + 65;
   st[i] = (char) m[i];
}
for(i = m.length - 1; i > 0; i = i - 1) {
   for(x : m) {
      m[i] = x + m[i-1];
      print(x);
   }
}

