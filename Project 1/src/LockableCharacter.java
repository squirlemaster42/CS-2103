import java.util.Random;

class LockableCharacter {
    private char _c;
    private boolean _isLocked;

    /**
     * Creates a character than can be locked.
     * When a character is locked it cannot be changed
     */
    LockableCharacter(){
        final Random rng = new Random();
        _c = (char) (rng.nextInt(26) + 'a');
        _isLocked = false;
    }

    /**
     * Sets _c to c if it is not locked
     * @param c The character to set _c to
     */
    void setChar(final char c){
        System.out.println(_isLocked + " " + c);
        if(!_isLocked){
            this._c = c;
            _isLocked = true;
        }
    }

    /**
     * Returns the character
     * @return a char
     */
    char getChar(){
        return _c;
    }

    /**
     * True if the char is locked
     * @return a boolean
     */
    boolean isLocked(){
        return _isLocked;
    }

    @Override
    public String toString(){
        return "" + _c;
    }
}
