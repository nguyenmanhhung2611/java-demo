/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Special case List implementation for returning fragments of Lists. This is useful for
 * when we know that we only want a small fragment of the data we asked for in an SQL 
 * query, but we need to know the full size of the results.
 * <p>
 * This list will report the fullSize as it's size() value, and it's iterator() will 
 * start and finish at the fragment start and end. All values outside the fragment 
 * will return null if they are address directly. e.g. 
 * <pre>
 * List&lt;String&gt; fragment = new ArrayList&lt;String&gt;();
 * fragment.add("5");
 * fragment.add("6");
 * 
 * List&lt;String&gt; wrapped = new ResultFragmentList&lt;String&gt;(fragment, 3, 10);
 * wrapped.size(); // will return 10
 * wrapped.get(1); // will return null
 * wrapped.get(3); // will return 5
 * wrapped.get(4); // will return 6
 * </pre>
 * This class is used by the {@link FragmentResultSetExtractor} internally when a 
 * {@link DAOCriteria} object has a fragment or offset value supplied.
 *  
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: ResultFragmentList.java,v 1.4 2007/06/13 06:32:03 rick Exp $
 */
public class ResultFragmentList<E> implements List<E> {

    private List<E> fragment;
    private int offset = 0;
    private int fullSize;
    
    public ResultFragmentList(List<E> fragment, int offset, int fullSize) {
        this.fragment = fragment;
        this.offset = offset;
        this.fullSize = fullSize;
    }
    
    public void add(int pIndex, E pElement) {
        this.fragment.add(pIndex - this.offset, pElement);
    }

    public boolean add(E pO) {
        return this.fragment.add(pO);
    }

    public boolean addAll(Collection<? extends E> pC) {
        return this.fragment.addAll(pC);
    }

    public boolean addAll(int pIndex, Collection<? extends E> pC) {
        return this.fragment.addAll(pIndex - this.offset, pC);
    }

    public void clear() {
        this.fragment.clear();
        this.fullSize = 0;
        this.offset = 0;
    }

    public boolean contains(Object pO) {
        return this.fragment.contains(pO);
    }

    public boolean containsAll(Collection<?> pC) {
        return this.fragment.containsAll(pC);
    }

    public E get(int pIndex) {
        int fragmentPosition = pIndex - this.offset;
        if ((fragmentPosition >= 0) && (fragmentPosition < this.fragment.size())) {
            return this.fragment.get(fragmentPosition);
        } else {
            return null;
        }
    }

    public int indexOf(Object pO) {
        int pos = this.fragment.indexOf(pO);
        return pos >= 0 ? pos + this.offset : pos;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public Iterator<E> iterator() {
        return this.fragment.iterator();
    }

    public int lastIndexOf(Object pO) {
        int pos = this.fragment.lastIndexOf(pO);
        return pos >= 0 ? pos + this.offset : pos;
    }

    public ListIterator<E> listIterator() {
        return this.fragment.listIterator();
    }

    public ListIterator<E> listIterator(int pIndex) {
        return this.fragment.listIterator(pIndex - this.offset);
    }

    public E remove(int pIndex) {
        return this.fragment.remove(pIndex - this.offset);
    }

    public boolean remove(Object pO) {
        return this.fragment.remove(pO);
    }

    public boolean removeAll(Collection<?> pC) {
        return this.fragment.removeAll(pC);
    }

    public boolean retainAll(Collection<?> pC) {
        return this.fragment.retainAll(pC);
    }

    public E set(int pIndex, E pElement) {
        return this.fragment.set(pIndex - this.offset, pElement);
    }

    public int size() {
        return this.fullSize;
    }

    public List<E> subList(int pFromIndex, int pToIndex) {
        return this.fragment.subList(pFromIndex - this.offset, pToIndex - this.offset);
    }

    public Object[] toArray() {
        return toArray(null);
    }

    @SuppressWarnings("unchecked")
    public Object[] toArray(Object[] pA) {
        Object out[] = null;
        int inSize = size();
        if ((pA == null) || (pA.length < inSize)) {
            out = new Object[inSize];
        } else {
            out = pA;
        }
        Object in = this.fragment.toArray();
        System.arraycopy(in, 0, out, 0, inSize);
        return out;
    }

}
