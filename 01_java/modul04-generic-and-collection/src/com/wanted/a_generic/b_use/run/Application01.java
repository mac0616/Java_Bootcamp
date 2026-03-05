package com.wanted.a_generic.b_use.run;

import com.wanted.a_generic.b_use.Bunny;
import com.wanted.a_generic.b_use.DrunkenBunny;
import com.wanted.a_generic.b_use.Rabbit;
import com.wanted.a_generic.b_use.RabbitFarm;

public class Application01 {

    public static void main(String[] args) {
        // Rabbit이나 Rabbit을 상속받은 것만 들어갈 수 있도록 설정해 Animal은 못 들어감.
//        RabbitFarm<Animal> farm1 = new RabbitFarm<>();

        RabbitFarm<Rabbit> farm2 = new RabbitFarm<Rabbit>(); // new RabbitFarm<Rabbit>에서 <> 안 Rabbit 들어가도 되고 안들어가도 ok
        RabbitFarm<Bunny> farm3 = new RabbitFarm<>();
        RabbitFarm<DrunkenBunny> farm4 = new RabbitFarm<>();

//        Rabbit rabbit = new Rabbit();
//        farm2.setAnimal(rabbit);          // 위 2줄과 아래 한줄은 같은 의미임.
        farm2.setAnimal(new Rabbit());
        farm2.getAnimal().cry();            // Rabbit rabbit = farm2.getAnimal();  rabbit.cry(); 2줄과 같은 의미.

        farm3.setAnimal(new Bunny());
        farm3.getAnimal().cry();

        farm4.setAnimal(new DrunkenBunny());
        farm4.getAnimal().cry();
    // farm4.getAnimal() = Rabbit 객체 ===> .cry(); 하기

    }

}
