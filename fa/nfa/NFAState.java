/**
 * NFAState.java
 * October 27, 2025
 * NFAState class that extends State class.
 * @author Tyler Fernandez; Sam Wilcox
 * 
 */
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

    /*
     * Puts the character into the transitions map with the corresponding set of states
     * @param symbol - the symbol of the transition
     * @param toStates - the set of states to add for the transition
     */
    public void putTransition(char symbol, Set<NFAState> toStates) {
        transitions.put(symbol, toStates);
    }
}
