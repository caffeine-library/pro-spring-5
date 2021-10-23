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

    public Long getId() {
        return id;
    }

    public boolean getOuterCommit() {
        return outerCommit;
    }

    public boolean getInnerCommit() {
        return innerCommit;
    }

}
