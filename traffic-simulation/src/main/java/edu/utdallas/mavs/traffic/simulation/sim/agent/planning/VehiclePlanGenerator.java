package edu.utdallas.mavs.traffic.simulation.sim.agent.planning;

import com.jme3.math.Vector3f;

import edu.utdallas.mavs.divas.core.sim.agent.knowledge.internal.Goal;
import edu.utdallas.mavs.divas.core.sim.agent.planning.AbstractPlanGenerator;
import edu.utdallas.mavs.divas.core.sim.agent.planning.Plan;
import edu.utdallas.mavs.divas.core.sim.agent.task.AbstractTask;
import edu.utdallas.mavs.divas.core.sim.agent.task.Task;
import edu.utdallas.mavs.traffic.simulation.sim.agent.knowledge.VehicleKnowledgeModule;
import edu.utdallas.mavs.traffic.simulation.sim.agent.tasks.MoveForwardTask;
import edu.utdallas.mavs.traffic.simulation.sim.agent.tasks.TurnTask;
import edu.utdallas.mavs.traffic.simulation.sim.agent.tasks.VehicleTaskModule;
import edu.utdallas.mavs.traffic.simulation.sim.common.state.Posture;

public class VehiclePlanGenerator extends AbstractPlanGenerator<VehicleKnowledgeModule, VehicleTaskModule> {
	private static final long serialVersionUID = 1L;
	private static final String POSITION = "Position";
	private Task ongoingTask;
	private boolean isIntersection = false;

	public VehiclePlanGenerator(VehicleKnowledgeModule knowledgeModule, VehicleTaskModule taskModule) {
		super(knowledgeModule, taskModule);
	}

	@Override
	public void addGoal(Goal arg0) {

	}

	@Override
	public Plan plan() {
		DrivePlan generatedPlan = new DrivePlan();
		if (hasOngoingTask()) {
			generatedPlan.addTask(ongoingTask);
		} else {
			planMovement(generatedPlan);
		}

		writeDebugInfo(generatedPlan);

		return generatedPlan;
	}

	private boolean hasOngoingTask() {
		return (ongoingTask instanceof TurnTask) && !((TurnTask) ongoingTask).hasCompleted();
	}

	private void planMovement(DrivePlan generatedPlan) {
		if (knowledgeModule.getFinalGoal() == null || knowledgeModule.getFinalGoal().isAchieved()) {
			Vector3f nextGoal = knowledgeModule.getNextGoal();
			knowledgeModule.setFinalGoal(new Goal("Vector", POSITION, nextGoal));
		}

		if (((Vector3f) knowledgeModule.getFinalGoal().getValue()) != null) {
			planNextPosition(generatedPlan);
		}
	}

	private void planNextPosition(DrivePlan generatedPlan) {
		float nextXPosition = 0;
		float nextZPosition = 0;
		Vector3f goal = (Vector3f) knowledgeModule.getFinalGoal().getValue();

		if (!knowledgeModule.getHeading().equals(knowledgeModule.getPreviousHeading())) {
			// This is the initial heading when creating vehicle agent. We don't
			// want the vehicle to stop there
			if (knowledgeModule.getPreviousHeading().equals(new Vector3f(0, 0, 0))) {
				knowledgeModule.setWaitTime(1);
			}

			if (knowledgeModule.getWaitTime() == 0) {
				knowledgeModule.setWaitTime(10);
				knowledgeModule.getSelf().setPosture(Posture.zero_position);
				isIntersection = true;
				knowledgeModule.getCurrentStreetTile();
			}

			// Move to the begging of the intersection before starting the turn
			// task
			if (knowledgeModule.getWaitTime() == 2) {
				MoveForwardTask moveForwardTask = taskModule
						.createMoveForwardTask(knowledgeModule.getSelf().getPosition()
								.add(knowledgeModule.getPreviousHeading().mult(VehicleKnowledgeModule.CAR_OFFSET)));
				generatedPlan.addTask(moveForwardTask);
				knowledgeModule.getSelf().setPosture(Posture.forward_fast);
			}

			if (knowledgeModule.getWaitTime() == 1) {

				/*
				 * if(!(ongoingTask instanceof TurnRightTask) &&
				 * knowledgeModule.yieldRightOfWay(intersectionTile)) {
				 * knowledgeModule.setWaitTime(knowledgeModule.getWaitTime() +
				 * 2); } else {
				 */

				isIntersection = false;
				knowledgeModule.setWaitTime(0);
				nextXPosition = goal.getX();
				nextZPosition = goal.getZ();
				knowledgeModule.getFinalGoal().setAchieved(true);
				ongoingTask = getNextTaskDirection();
				generatedPlan.addTask(ongoingTask);
				// }
			} else {
				knowledgeModule.setWaitTime(knowledgeModule.getWaitTime() - 1);
			}

		} else if (knowledgeModule.getWaitTime() == 0) {
			// if the vehicle movement on the X-axis
			if (Math.abs(knowledgeModule.getHeading().getX()) == 1) {
				nextXPosition = knowledgeModule.getSelf().getPosition().getX()
						+ (knowledgeModule.desiredSpeed * knowledgeModule.getHeading().getX());
				nextZPosition = knowledgeModule.getSelf().getPosition().getZ();

				if (((nextXPosition >= (goal.getX())) && (knowledgeModule.getHeading().getX() == 1))
						|| ((nextXPosition <= (goal.getX())) && (knowledgeModule.getHeading().getX() == -1))) {
					nextXPosition = goal.getX();
				}

				// the current goal is reached
				if (nextXPosition == goal.getX()) {
					knowledgeModule.getFinalGoal().setAchieved(true);
					if (isIntersection) {
						isIntersection = false;
						knowledgeModule.setWaitTime(10);
						knowledgeModule.getSelf().setPosture(Posture.zero_position);
					}
				}
			}

			// if the vehicle movement on the Z-axis
			if (Math.abs(knowledgeModule.getHeading().getZ()) == 1) {
				nextZPosition = knowledgeModule.getSelf().getPosition().getZ()
						+ (knowledgeModule.desiredSpeed * knowledgeModule.getHeading().getZ());
				nextXPosition = knowledgeModule.getSelf().getPosition().getX();

				if (((nextZPosition >= (goal.getZ())) && (knowledgeModule.getHeading().getZ() == 1))
						|| ((nextZPosition <= (goal.getZ())) && (knowledgeModule.getHeading().getZ() == -1))) {
					nextZPosition = goal.getZ();
				}
				// the current goal is reached
				if (nextZPosition == goal.getZ()) {
					knowledgeModule.getFinalGoal().setAchieved(true);
					if (isIntersection) {
						isIntersection = false;
						knowledgeModule.setWaitTime(10);
						knowledgeModule.getSelf().setPosture(Posture.zero_position);
					}
				}
			}

			Vector3f nextPosition = new Vector3f(nextXPosition, knowledgeModule.getSelf().getPosition().getY(),
					nextZPosition);

			if (knowledgeModule.collidesWithOtherVehicles(nextPosition)) {
				knowledgeModule.setWaitTime(1);
				knowledgeModule.getSelf().setPosture(Posture.zero_position);
			} else {
				MoveForwardTask moveForwardTask = taskModule.createMoveForwardTask(nextPosition);
				generatedPlan.addTask(moveForwardTask);
				if (knowledgeModule.getWaitTime() != 10) {
					knowledgeModule.getSelf().setPosture(Posture.forward_fast);
				}
			}

			if (knowledgeModule.getCurrentGoalDegree() > 2) {
				isIntersection = true;
				knowledgeModule.getCurrentStreetTile();
			} else {
				isIntersection = false;
			}
		} else {
			knowledgeModule.setWaitTime(knowledgeModule.getWaitTime() - 1);
		}
	}

	/**
	 * Return the type of next task
	 */
	public AbstractTask getNextTaskDirection() {
		if (knowledgeModule.getPreviousHeading().getX() == 1 && knowledgeModule.getHeading().getZ() == 1) {
			// right
			Vector3f nextPosition = (Vector3f) knowledgeModule.getFinalGoal().getValue();
			nextPosition.setZ(nextPosition.getZ());
			knowledgeModule.getSelf().setPosture(Posture.forward_mid_right);
			return taskModule.createTurnRightTask(nextPosition);
		} else if (knowledgeModule.getPreviousHeading().getX() == 1 && knowledgeModule.getHeading().getZ() == -1) {
			// left
			Vector3f nextPosition = (Vector3f) knowledgeModule.getFinalGoal().getValue();
			nextPosition.setZ(nextPosition.getZ());
			knowledgeModule.getSelf().setPosture(Posture.forward_mid_left);
			return taskModule.createTurnLeftTask(nextPosition);
		} else if (knowledgeModule.getPreviousHeading().getX() == -1 && knowledgeModule.getHeading().getZ() == 1) {
			// left
			Vector3f nextPosition = (Vector3f) knowledgeModule.getFinalGoal().getValue();
			nextPosition.setZ(nextPosition.getZ());
			knowledgeModule.getSelf().setPosture(Posture.forward_mid_left);
			return taskModule.createTurnLeftTask(nextPosition);
		} else if (knowledgeModule.getPreviousHeading().getX() == -1 && knowledgeModule.getHeading().getZ() == -1) {
			// right
			Vector3f nextPosition = (Vector3f) knowledgeModule.getFinalGoal().getValue();
			nextPosition.setZ(nextPosition.getZ());
			knowledgeModule.getSelf().setPosture(Posture.forward_mid_right);
			return taskModule.createTurnRightTask(nextPosition);
		} else if (knowledgeModule.getPreviousHeading().getZ() == 1 && knowledgeModule.getHeading().getX() == 1) {
			// left
			Vector3f nextPosition = (Vector3f) knowledgeModule.getFinalGoal().getValue();
			nextPosition.setX(nextPosition.getX());
			knowledgeModule.getSelf().setPosture(Posture.forward_mid_left);
			return taskModule.createTurnLeftTask(nextPosition);
		} else if (knowledgeModule.getPreviousHeading().getZ() == 1 && knowledgeModule.getHeading().getX() == -1) {
			// right
			Vector3f nextPosition = (Vector3f) knowledgeModule.getFinalGoal().getValue();
			nextPosition.setX(nextPosition.getX());
			knowledgeModule.getSelf().setPosture(Posture.forward_mid_right);
			return taskModule.createTurnRightTask(nextPosition);
		} else if (knowledgeModule.getPreviousHeading().getZ() == -1 && knowledgeModule.getHeading().getX() == 1) {
			// right
			Vector3f nextPosition = (Vector3f) knowledgeModule.getFinalGoal().getValue();
			nextPosition.setX(nextPosition.getX());
			knowledgeModule.getSelf().setPosture(Posture.forward_mid_right);
			return taskModule.createTurnRightTask(nextPosition);
		} else if (knowledgeModule.getPreviousHeading().getZ() == -1 && knowledgeModule.getHeading().getX() == -1) {
			// left
			Vector3f nextPosition = (Vector3f) knowledgeModule.getFinalGoal().getValue();
			nextPosition.setX(nextPosition.getX());
			knowledgeModule.getSelf().setPosture(Posture.forward_mid_left);
			return taskModule.createTurnLeftTask(nextPosition);
		}

		return null;
	}

	private void writeDebugInfo(DrivePlan generatedPlan) {
		if (generatedPlan != null) {
			/*
			 * knowledgeModule.getSelf().setPlanningDetails( String.format("%s",
			 * generatedPlan.toString()));
			 */
			// knowledgeModule.getSelf().setPlanningDetails(String.format("Current
			// Tile id: %d",
			// knowledgeModule.getCurrentStreetTile().getID()));
		}
	}
}
