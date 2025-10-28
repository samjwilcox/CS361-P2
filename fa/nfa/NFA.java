/**
 * NFA.java
 * October 27, 2025
 * This class implements a Non-deterministic Finite Automaton (NFA).
 * It provides methods to add states, transitions, and check if a string is accepted by the NFA.
 * @author Tyler Fernandez; Sam Wilcox
 * 
 */

package fa.nfa;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Stack;

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
        if (startState == null) {
            return false;
        }
        // Start with the epsilon-closure of the start state
        Set<NFAState> currentStates = eClosure(startState);

        // Process each input symbol iteratively
        for (int i = 0; i < s.length(); i++) {
            char symb = s.charAt(i);
            Set<NFAState> nextStates = new LinkedHashSet<>();
            // For every active state, add all targets on symb
            for (NFAState st : currentStates) {
                Set<NFAState> targets = getToState(st, symb);
                if (targets != null) {
                    nextStates.addAll(targets);
                }
            }
            // Take epsilon-closure of the whole next set
            Set<NFAState> closure = new LinkedHashSet<>();
            for (NFAState st : nextStates) {
                closure.addAll(eClosure(st));
            }
            currentStates = closure;
            if (currentStates.isEmpty()) {
                return false; // no path can continue
            }
        }
        // After consuming input, if any active state is final -> accept
        for (NFAState st : currentStates) {
            if (finalStates.contains(st)) return true;
        }
        return false;
    }

    @Override
    public Set<Character> getSigma() {
        return sigma;
    }

    @Override
    public NFAState getState(String name) {
        Iterator<NFAState> iter = states.iterator();
        while (iter.hasNext()) { // Iterate through states
            NFAState state = iter.next();
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
        return from.transitions.get(onSymb);
    }

    @Override
    public Set<NFAState> eClosure(NFAState s) {

        NFAState nfaState = (NFAState) getState(s.getName());
        
        Set<NFAState> closure = new LinkedHashSet<>();
        Stack<NFAState> stack = new Stack<>();
        
        // Push the initial state onto the stack and add to closure
        stack.push(nfaState);
        closure.add(nfaState);
        
        // Continue while there are states to process
        while (!stack.isEmpty()) {
            NFAState current = stack.pop();
            // Get all states reachable through epsilon transitions
            Set<NFAState> epsilonTransitions = getToState(current, 'e');
            
            // If there are epsilon transitions from current state
            if (epsilonTransitions != null) {
                for (NFAState nextState : epsilonTransitions) {
                    // If we haven't seen this state before
                    if (!closure.contains(nextState)) {
                        closure.add(nextState);
                        stack.push(nextState);
                    }
                }
            }
        }
        return closure;
    }

    @Override
    public int maxCopies(String s) {
        if (startState == null) {
            return 0;
        }
        Set<NFAState> currentStates = eClosure(startState);
        int max = currentStates.size();

        for (int i = 0; i < s.length(); i++) {
            char symb = s.charAt(i);
            Set<NFAState> nextStates = new LinkedHashSet<>();
            for (NFAState state : currentStates) {
                Set<NFAState> toStates = getToState(state, symb);
                if (toStates != null) {
                    nextStates.addAll(toStates);
                }
            }
            // take epsilon closure of all next states
            Set<NFAState> closure = new LinkedHashSet<>();
            for (NFAState st : nextStates) {
                closure.addAll(eClosure(st));
            }
            currentStates = closure;
            if (currentStates.size() > max) max = currentStates.size();
        }
        // At least 1 copy exists initially (if startState present)
        return Math.max(1, max);
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

        // Merge with existing transitions for the symbol if present
        Set<NFAState> existing = from.transitions.get(onSymb);
        if (existing == null) {
            from.putTransition(onSymb, toStateSet);
        } else {
            Set<NFAState> merged = new LinkedHashSet<>(existing);
            merged.addAll(toStateSet);
            from.putTransition(onSymb, merged);
        }
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
