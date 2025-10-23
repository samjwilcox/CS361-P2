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

    /*
     * Constructor
     */
    public NFA() {
        // Initialize instance variables
        this.states = new LinkedHashSet<>();
        this.sigma = new LinkedHashSet<>();
        this.finalStates = new LinkedHashSet<>();
        this.startState = null;
    }

    @Override
    public boolean addState(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addState'");
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getState'");
    }

    @Override
    public boolean isFinal(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isFinal'");
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addTransition'");
    }

    @Override
    public boolean isDFA() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isDFA'");
    }
    
}
