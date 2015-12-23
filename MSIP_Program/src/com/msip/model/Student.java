package com.msip.model;

public class Student extends Person {

	 enum ParcipitationState {
		INACTIVE, ACTIVE
	}

	private ParcipitationState parcipitationState = ParcipitationState.INACTIVE;

	public Student(String firstName, String lastName, int kNumber, String pHash, ParcipitationState state) {
		super(firstName, lastName, kNumber, pHash);
		setParcipitationState(state);
	}

	/**
	 * @return the parcipitationState
	 */
	public ParcipitationState getParcipitationState() {
		return parcipitationState;
		//TODO participation state will be dynamic?
	}

	/**
	 * @param parcipitationState the parcipitationState to set
	 */
	public void setParcipitationState(ParcipitationState parcipitationState) {
		this.parcipitationState = parcipitationState;
	}

}
