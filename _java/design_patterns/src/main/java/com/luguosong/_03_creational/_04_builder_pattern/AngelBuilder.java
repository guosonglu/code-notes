package com.luguosong._03_creational._04_builder_pattern;

/**
 * 天使角色生成器，充当具体生成器
 *
 * @author 10545
 * @date 2022/3/9 23:01
 */
public class AngelBuilder extends ActorBuilder{
    @Override
    public void buildType() {
        actor.setType("天使");
    }

    @Override
    public void buildSex() {
        actor.setSex("女");
    }

    @Override
    public void buildFace() {
        actor.setFace("漂亮");
    }

    @Override
    public void buildCostume() {
        actor.setCostume("白裙");
    }

    @Override
    public void buildHairstyle() {
        actor.setHairstyle("披肩长发");
    }
}
