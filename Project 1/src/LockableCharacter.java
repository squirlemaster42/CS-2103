import java.util.Random;

//TODO Comment
class LockableCharacter {
    private char c;
    private boolean isLocked;

    LockableCharacter(){
        final Random rng = new Random();
        c = (char) (rng.nextInt(26) + 'a');
        isLocked = false;
    }

    char getChar(){
        return c;
    }

    boolean getIsLocked(){
        return isLocked;
    }

    void lock(){
        if(!isLocked){
            isLocked = true;
        }
    }
}
