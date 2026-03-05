package com.wanted.a_generic.b_use;
// <T는 타입 다 들어올 수 있지만 상속(extends)을 함으로써 Rabbit인 애들만 들어올 수 있음>

/* T <- 타입변수에는 어떤 값이 들어올지 모른다.
*  단, extends Rabbit 이라고 작성을 하게 되면
*  T 타입변수에는 Rabbit 혹은 Rabbit을 상속 받은 클래스만 들어올 수 있게 된다. */

// 제네릭 클래스
public class RabbitFarm<T extends Rabbit> {
    private T animal;

    public RabbitFarm() {}

    public RabbitFarm(T animal) {
        this.animal = animal;
    }

    public T getAnimal() {
        return animal;
    }

    public void setAnimal(T animal) {
        this.animal = animal;
    }
}
