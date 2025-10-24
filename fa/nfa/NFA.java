package fa.nfa;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import fa.State;

public class NFA implements NFAInterface {

    // Instance Variables
    private Set<NFAState> states;
    private Set<Character> sigma;
    private NFAState startState;
    private Set<NFAState> finalStates;
    private Boolean containsEpsilon;

    /*
     * Constructor
     */
    public NFA() {
        // Initialize instance variables
        this.states = new LinkedHashSet<>();
        this.sigma = new LinkedHashSet<>();
        this.finalStates = new LinkedHashSet<>();
        this.startState = null;
        this.containsEpsilon = false;
    }

    @Override
    public boolean addState(String name) {

        // Check if state with the same name already exists
        Iterator<NFAState> iter = states.iterator();
        while (iter.hasNext()) {
            NFAState state = (NFAState) iter.next();
            if (state.getName().equals(name)) { // State already exists, return false
                return false;
            }
        }

        states.add(new NFAState(name));
        return true;
    }

    @Override
    public boolean setFinal(String name) {
        Iterator<NFAState> iter = states.iterator();
        while (iter.hasNext()) {
            NFAState state = (NFAState) iter.next();
            if (state.getName().equals(name)) { // Found the state, add to finalStates, return true
                finalStates.add(state);
                return true;
            }
        }
        return false; // Name not in states, return false
    }

    @Override
    public boolean setStart(String name) {
        Iterator<NFAState> iter = states.iterator();
        while (iter.hasNext()) {
            NFAState state = (NFAState) iter.next();
            if (state.getName().equals(name)) { // Found the state, set as startState, return true
                startState = state;
                return true;
            }
        }
        return false; // Name not in states, return false
    }

    @Override
    public void addSigma(char symbol) {
        sigma.add(symbol);
    }

    @Override
    public boolean accepts(String s) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'accepts'");
    }

    @Override
    public Set<Character> getSigma() {
        return sigma;
    }

    @Override
    public State getState(String name) {
        Iterator<NFAState> iter = states.iterator();
        while (iter.hasNext()) { // Iterate through states
            NFAState state = (NFAState) iter.next();
            if (state.getName().equals(name)) { // Found the state, return it
                return state;
            }
        }
        return null; // Name not in states, return null
    }

    @Override
    public boolean isFinal(String name) {
        Iterator<NFAState> iter = finalStates.iterator();
        while (iter.hasNext()) {
            NFAState state = (NFAState) iter.next();
            if (state.getName().equals(name)) { // Found the state in finalStates, return true
                return true;
            }
        }
        return false; // Name not in finalStates, return false
    }

    @Override
    public boolean isStart(String name) {
        if (startState.getName().equals(name)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Set<NFAState> getToState(NFAState from, char onSymb) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getToState'");
    }

    @Override
    public Set<NFAState> eClosure(NFAState s) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eClosure'");
    }

    @Override
    public int maxCopies(String s) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'maxCopies'");
    }

    @Override
    public boolean addTransition(String fromState, Set<String> toStates, char onSymb) {
        if (onSymb == 'e') { // Update containsEpsilon if epsilon transition is added
            containsEpsilon = true;
        }

        if (!sigma.contains(onSymb) && onSymb != 'e') {
            return false; // Symbol not in alphabet and isn't epsilon
        }
        NFAState from = (NFAState) getState(fromState);
        if (from == null) {
            return false; // From state does not exist
        }

        Set<NFAState> toStateSet = new LinkedHashSet<>();
        for (String toStateName : toStates) {
            NFAState toState = (NFAState) getState(toStateName);
            if (toState == null) {
                return false; // To state does not exist
            }
            toStateSet.add(toState);
        }

        from.putTransition(onSymb, toStateSet);
        return true;
    }

    @Override
    public boolean isDFA() {
        if (containsEpsilon) {
            return false; // NFA has epsilon transitions, cannot be DFA
        }
        for (NFAState state : states) {
            for (char symbol : sigma) {
                Set<NFAState> toStates = state.transitions.get(symbol);
                if (toStates == null || toStates.size() != 1) {
                    return false; // Either no transition or multiple transitions for a symbol
                }
            }
        }

        return true; // All states have exactly one transition for each symbol
    }
    
}
