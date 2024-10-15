package org.csgeeks.myapplication.model;

import org.springframework.core.style.ToStringCreator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name="point_buy_choices")
public class PointBuyChoice {

    public static final PointBuyChoice NULL_CHOICE = new PointBuyChoice(-1, -1, -1, -1, -1, -1);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "attr_1")
    private Integer attribute1;

    @Column(name = "attr_2")
    private Integer attribute2;

    @Column(name = "attr_3")
    private Integer attribute3;

    @Column(name = "attr_4")
    private Integer attribute4;

    @Column(name = "attr_5")
    private Integer attribute5;

    @Column(name = "attr_6")
    private Integer attribute6;

    protected PointBuyChoice() { }

    public PointBuyChoice(Integer attribute1, Integer attribute2, Integer attribute3, Integer attribute4, Integer attribute5, Integer attribute6) {
	this.attribute1 = attribute1;
	this.attribute2 = attribute2;
	this.attribute3 = attribute3;
	this.attribute4 = attribute4;
	this.attribute5 = attribute5;
	this.attribute6 = attribute6;
    }

    public Integer getId() {
	return this.id;
    }

    public PointBuyChoice setId(Integer id) {
	this.id = id;
	return this;
    }

    public boolean isNew() {
	return this.id == null;
    }

    public Integer getAttribute1() {
	return this.attribute1;
    }

    public void setAttribute1(Integer attribute1) {
	this.attribute1 = attribute1;
    }

    public Integer getAttribute2() {
	return this.attribute2;
    }

    public void setAttribute2(Integer attribute2) {
	this.attribute2 = attribute2;
    }

    public Integer getAttribute3() {
	return this.attribute3;
    }

    public void setAttribute3(Integer attribute3) {
	this.attribute3 = attribute3;
    }

    public Integer getAttribute4() {
	return this.attribute4;
    }

    public void setAttribute4(Integer attribute4) {
	this.attribute4 = attribute4;
    }

    public Integer getAttribute5() {
	return this.attribute5;
    }

    public void setAttribute5(Integer attribute5) {
	this.attribute5 = attribute5;
    }

    public Integer getAttribute6() {
	return this.attribute6;
    }

    public void setAttribute6(Integer attribute6) {
	this.attribute6 = attribute6;
    }

    @Override
    public String toString() {
	return new ToStringCreator(this).append("id", this.getId())
	    .append("new", this.isNew())
	    .append("attr_1", this.getAttribute1())
	    .append("attr_2", this.getAttribute2())
	    .append("attr_3", this.getAttribute3())
	    .append("attr_4", this.getAttribute4())
	    .append("attr_5", this.getAttribute5())
	    .append("attr_6", this.getAttribute6())
	    .toString();
    }
}
