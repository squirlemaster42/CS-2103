public class C implements X,Y{
    @Override
    public void m(Object o) {

    }

    @Override
    public X n() {
        return new X();
    }

    @Override
    public int p(int j) {
        return 0;
    }

    public class B extends A{

        @Override
        void q() {

        }
    }
    @Override
    public A me() {

        B b = new B();
        return b;
    }

}
