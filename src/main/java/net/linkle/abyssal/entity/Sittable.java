package net.linkle.abyssal.entity;

public interface Sittable {
    boolean isSitting();
    void setSitting(boolean sitting);
    void setInSittingPose(boolean inSittingPose);
    boolean isInSittingPose();
}
