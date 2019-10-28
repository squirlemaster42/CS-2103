import java.util.Random;

//TODO Comment
//TODO mke code pretty
class LockableCharacter {
    private char c;
    private boolean isLocked;

    LockableCharacter(){
        final Random rng = new Random();
        c = (char) (rng.nextInt(26) + 'a');
        isLocked = false;
    }

    void setChar(final char c){
        System.out.println(isLocked + " " + c);
        if(!isLocked){
            this.c = c;
            isLocked = true;
        }
    }

    char getChar(){
        return c;
    }

    boolean getIsLocked(){
        return isLocked;
    }

    @Override
    public String toString(){
        return "" + c;
    }
}
