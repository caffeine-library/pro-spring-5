package org.binchoo.env.propagation.entities;

import javax.persistence.*;

@Entity
public class SimpleData {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private boolean outerCommit = false;

    @Column(nullable = false)
    private boolean innerCommit = false;

    public void setOuterCommit(boolean c) {
        this.outerCommit = c;
    }

    public void setInnerCommit(boolean c) {
        this.innerCommit = c;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public boolean getOuterCommit() {
        return outerCommit;
    }

    public boolean getInnerCommit() {
        return innerCommit;
    }

    @Override
    public String toString() {
        return id + "," + innerCommit + "," + outerCommit;
    }
}
