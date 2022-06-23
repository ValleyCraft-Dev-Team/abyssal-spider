package net.linkle.abyssal.client.entity.model;

import net.linkle.abyssal.entity.TamedAbyssalSpider;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SpiderEntityModel;
import net.minecraft.util.math.MathHelper;

public class TamedSpiderEntityModel extends SpiderEntityModel<TamedAbyssalSpider> {
    
    private final ModelPart head;
    private final ModelPart root;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightMiddleLeg;
    private final ModelPart leftMiddleLeg;
    private final ModelPart rightMiddleFrontLeg;
    private final ModelPart leftMiddleFrontLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;

    public TamedSpiderEntityModel(ModelPart root) {
        super(root);
        this.root = root;
        this.head = root.getChild(EntityModelPartNames.HEAD);
        this.rightHindLeg = root.getChild(EntityModelPartNames.RIGHT_HIND_LEG);
        this.leftHindLeg = root.getChild(EntityModelPartNames.LEFT_HIND_LEG);
        this.rightMiddleLeg = root.getChild("right_middle_hind_leg");
        this.leftMiddleLeg = root.getChild("left_middle_hind_leg");
        this.rightMiddleFrontLeg = root.getChild("right_middle_front_leg");
        this.leftMiddleFrontLeg = root.getChild("left_middle_front_leg");
        this.rightFrontLeg = root.getChild(EntityModelPartNames.RIGHT_FRONT_LEG);
        this.leftFrontLeg = root.getChild(EntityModelPartNames.LEFT_FRONT_LEG);
    }
    
    @Override
    public void setAngles(TamedAbyssalSpider entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        head.yaw = headYaw * (float) (Math.PI / 180.0);
        head.pitch = headPitch * (float) (Math.PI / 180.0);
        
        rightHindLeg.roll = (float) (-Math.PI / 4);
        leftHindLeg.roll = (float) (Math.PI / 4);
        rightMiddleLeg.roll = -0.58119464F;
        leftMiddleLeg.roll = 0.58119464F;
        rightMiddleFrontLeg.roll = -0.58119464F;
        leftMiddleFrontLeg.roll = 0.58119464F;
        rightFrontLeg.roll = (float) (-Math.PI / 4);
        leftFrontLeg.roll = (float) (Math.PI / 4);
        rightHindLeg.yaw = (float) (Math.PI / 4);
        leftHindLeg.yaw = (float) (-Math.PI / 4);
        rightMiddleLeg.yaw = (float) (Math.PI / 8);
        leftMiddleLeg.yaw = (float) (-Math.PI / 8);
        rightMiddleFrontLeg.yaw = (float) (-Math.PI / 8);
        leftMiddleFrontLeg.yaw = (float) (Math.PI / 8);
        rightFrontLeg.yaw = (float) (-Math.PI / 4);
        leftFrontLeg.yaw = (float) (Math.PI / 4);
        
        if (entity.isInSittingPose()) {
            var roll = 0.4f;
            rightHindLeg.roll += roll;
            leftHindLeg.roll += -roll;
            rightMiddleLeg.roll += roll;
            leftMiddleLeg.roll += -roll;
            rightMiddleFrontLeg.roll += roll;
            leftMiddleFrontLeg.roll += -roll;
            rightFrontLeg.roll += roll;
            leftFrontLeg.roll += -roll;
            var spread = 1.3f;
            rightHindLeg.yaw *= spread;
            leftHindLeg.yaw *= spread;
            rightMiddleLeg.yaw *= spread;
            leftMiddleLeg.yaw *= spread;
            rightMiddleFrontLeg.yaw *= spread;
            leftMiddleFrontLeg.yaw *= spread;
            rightFrontLeg.yaw *= spread;
            leftFrontLeg.yaw *= spread;
            root.pivotY = 5f;
        } else {
            root.pivotY = 0;
        }
        
        float i = -(MathHelper.cos(limbAngle * 0.6662F * 2.0F + 0.0F) * 0.4F) * limbDistance;
        float j = -(MathHelper.cos(limbAngle * 0.6662F * 2.0F + (float) Math.PI) * 0.4F) * limbDistance;
        float k = -(MathHelper.cos(limbAngle * 0.6662F * 2.0F + (float) (Math.PI / 2)) * 0.4F) * limbDistance;
        float l = -(MathHelper.cos(limbAngle * 0.6662F * 2.0F + (float) (Math.PI * 3.0 / 2.0)) * 0.4F) * limbDistance;
        float m = Math.abs(MathHelper.sin(limbAngle * 0.6662F + 0.0F) * 0.4F) * limbDistance;
        float n = Math.abs(MathHelper.sin(limbAngle * 0.6662F + (float) Math.PI) * 0.4F) * limbDistance;
        float o = Math.abs(MathHelper.sin(limbAngle * 0.6662F + (float) (Math.PI / 2)) * 0.4F) * limbDistance;
        float p = Math.abs(MathHelper.sin(limbAngle * 0.6662F + (float) (Math.PI * 3.0 / 2.0)) * 0.4F) * limbDistance;
        rightHindLeg.yaw += i;
        leftHindLeg.yaw += -i;
        rightMiddleLeg.yaw += j;
        leftMiddleLeg.yaw += -j;
        rightMiddleFrontLeg.yaw += k;
        leftMiddleFrontLeg.yaw += -k;
        rightFrontLeg.yaw += l;
        leftFrontLeg.yaw += -l;
        rightHindLeg.roll += m;
        leftHindLeg.roll += -m;
        rightMiddleLeg.roll += n;
        leftMiddleLeg.roll += -n;
        rightMiddleFrontLeg.roll += o;
        leftMiddleFrontLeg.roll += -o;
        rightFrontLeg.roll += p;
        leftFrontLeg.roll += -p;
    }
}
