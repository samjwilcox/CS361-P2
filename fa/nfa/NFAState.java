package fa.nfa;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import fa.State;
/*
 * NFAState class that extends State class
 * @author Sam Wilcox; Tyler Fernandez
 */
public class NFAState extends State {

    // Instance Variables
    private String name;
    Map<Character, Set<NFAState>> transitions = new HashMap<>();

    /*
     * Constructor that creates a new NFAState object
     * @param name - the name of the state
     */
    public NFAState(String name) {
        super(name);
        this.name = name;
    }

    /*
     * Returns the name of the state
     * @return name - the name of the state
     */
    public String getName() {
        return name;
    }
}
