package com.tian.server.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.tian.server.common.ArmorType;
import com.tian.server.common.EquipType;
import com.tian.server.common.GoodsType;
import com.tian.server.entity.EquipmentEntity;
import com.tian.server.model.Goods;
import com.tian.server.model.Living;
import com.tian.server.model.Race.Human;
import netscape.security.Target;

/**
 * Created by PPX on 2017/8/17.
 */
public class EquipService {

    public Integer wear(Living owner, Goods goods) {

        // Only character object can wear armors.
        if(!(owner instanceof Human)) {

            return 0;
        }

        if(goods.query("no_identify") != null && (Boolean)goods.query("no_identify")){
            //return notify_fail(this_object()->name() + "需要鉴定后才可以使用。\n");
        }

        if((Integer)goods.query("consistence") < 1 ) {
            //return notify_fail(this_object()->name() + "现在损坏太严重了，不能继续穿戴了。\n");
        }

        // If already worn, just recognize it.
        if( (Boolean)goods.query("equipped") == true ) return 1;

        // If handing it now, stop handing
        if( owner.queryTemp("handing").equals(goods) ){
            owner.deleteTemp("handing");
        }

        /*if(goods.getBaseInfo().getType().intValue() != GoodsType.EQPT.toInteger()
                || ((EquipmentEntity)goods.getRefEntity()).getMainClass().intValue() != EquipType.ARMOR.toInteger()){
            //notify_fail("你只能穿戴可当作护具的东西。\n");
            return 0;
        }

        EquipmentEntity equip = (EquipmentEntity)goods.getRefEntity();

        // Check if we have "armor_prop" defined.
        if (equip.getSubClass().intValue() < 1){
            //notify_fail("你只能穿戴可当作护具的东西。\n");
            return 0;
        }

        ArmorType armorType =  ArmorType.valueOf(equip.getSubClass().intValue());

        if( owner.queryTemp("armor/" + armorType.toString().toLowerCase()) != null ) {
            //return notify_fail("你已经穿戴了同类型的护具了。\n");
        }


        if(owner.queryTemp("weapon") != null && armorType.toString().toLowerCase().equals("hands")) {

            //return notify_fail("你手中拿着武器如何戴手套？\n");//by Ciwei@SJ
        }*/





        /*if( query("broken") ) return notify_fail("这件防具已经损坏了。\n");





        owner->set_temp("armor/" + type, this_object());


        apply = keys(armor_prop);


        applied_prop = owner->query_temp("apply");


        if( !mapp(applied_prop) ) applied_prop = ([]);


        for(int i = 0; i<sizeof(apply); i++)


            if( undefinedp(applied_prop[apply[i]]) )


                applied_prop[apply[i]] = armor_prop[apply[i]];


            else


                applied_prop[apply[i]] += armor_prop[apply[i]];


        owner->set_temp("apply", applied_prop);


        set("equipped", "worn");*/


        return 1;


    }





    /*int wield()


    {


        object owner, old_weapon;


        mapping weapon_prop;


        string *apply;


        int flag;





        // Only character object can wear armors.


        if (!environment()) return 0;


        if( !(owner = environment())->is_character() ) return 0;





        // If already wielded, just recognize it.


        if( query("equipped") ) return 1;





        // Check if we have "weapon_prop" defined.


        if (!query("skill_type") || !mapp(weapon_prop = query("weapon_prop")) )


            return notify_fail("你只能装备可当作武器的东西。\n");


        if( owner->query_temp("armor/hands") )


            return notify_fail("你穿戴着手套拿不了其他武器了。\n"); //by Ciwei@SJ


        if( query("broken") ) return notify_fail("这件武器已经损坏了。\n");


        flag = query("flag");





        if( flag & TWO_HANDED ) {


            if( owner->query_temp("weapon")


                    || owner->query_temp("secondary_weapon")


                    || owner->query_temp("armor/shield") )


                return notify_fail("你必须空出双手才能装备双手武器。\n");


            owner->set_temp("weapon", this_object());


        }


        else {


            // If we are are using any weapon?


            if( !(old_weapon = owner->query_temp("weapon")) )


                owner->set_temp("weapon", this_object());





            else // If we still have a free hand?


                if( !owner->query_temp("secondary_weapon")


                        && !owner->query_temp("armor/shield") ) {





                    // If we can wield this as secondary weapon?


                    if(flag & SECONDARY) {


                        owner->set_temp("secondary_weapon", this_object());


                        // If we can switch our old weapon to secondary weapon ?


                    } else if( (int)old_weapon->query("flag") & SECONDARY ) {


                        old_weapon->unequip();


                        owner->set_temp("weapon", this_object());


                        old_weapon->wield();


                        // We need unwield our old weapon before we can use this one.


                    } else


                        return notify_fail("你必须先放下你目前装备的武器。\n");





                    // We have both hands wearing something.


                } else


                    return notify_fail("你必须空出一只手来使用武器。\n");


        }





        apply = keys(weapon_prop);


        for(int i = 0; i<sizeof(apply); i++)


            owner->add_temp("apply/" + apply[i], weapon_prop[apply[i]]);





        owner->reset_action();


        set("equipped", "wielded");


        return 1;


    }





    int unequip()


    {


        object owner;


        mapping prop, applied_prop;


        string *apply, equipped;





        if( !(owner = environment()) ) return 0;


        if( !stringp(equipped = query("equipped")) )


            //return notify_fail("你目前并没有装备这样东西。\n");


            return 1;//unequip success so return 1! for move()  by Ciwei@SJ





        if( equipped=="wielded" ) {


            if( (object)owner->query_temp("weapon") == this_object() )


                owner->delete_temp("weapon");


            else if( (object)owner->query_temp("secondary_weapon") == this_object() )


                owner->delete_temp("secondary_weapon");


            prop = query("weapon_prop");


            owner->reset_action();


        } else if( equipped=="worn" ) {


            owner->delete_temp("armor/" + query("armor_type") );


            prop = query("armor_prop");


        }





        apply = keys(prop);


        applied_prop = owner->query_temp("apply");


        if (!applied_prop) applied_prop = ([]);


        for(int i = 0; i<sizeof(apply); i++)


            // To support array apply, we cannot add_temp() here.


            applied_prop[apply[i]] -= prop[apply[i]];





        delete("equipped");


        return 1;


    }





    int broken(string str)


    {


        object victim = environment(this_object());





        if( query("no_broken") ) return 0;


        if( !mapp(query("weapon_prop")) && !mapp(query("armor_prop")) ) return 0;





        unequip();





        if( victim->is_character() ) {


            this_object()->move(environment(victim));


            if( mapp(query("weapon_prop")) ) victim->reset_action();


        }





        if(victim)


        {


            this_object()->delete_weapon2(victim);


            this_object()->delete_armor2(victim);


        }





        if( (int)query("imbued")>3 ) {


            delete("owner");


            destruct(this_object());


            return 1;


        }





        if( !str ) str = "损坏的";





        delete_temp("apply");


        delete("weapon_mp/owner");


        delete("armor_mp/owner");


        set_temp("apply/name_old", query("name"));


        set_temp("apply/weapon_prop_old", query("weapon_prop"));


        set_temp("apply/armor_prop_old", query("armor_prop"));


        set_temp("apply/long_old", query("long"));


        set_temp("apply/value", query("value"));


        set_temp("apply/weapon_mp_old",query("weapon_mp"));


        set_temp("apply/armor_mp_old",query("armor_mp"));





        set("name", str + query("name"));


        set("value", 49);


        set("broken", 1);


        set("weapon_prop", 0);


        set("armor_prop", 0);


        set("treasure", 0);


        set("long", (string)query("long")+"\n但是它已经损坏，再也无法使用了。\n");





        delete("owner");


        delete("weapon_mp");


        delete("armor_mp");





        return 1;


    }





    int do_embed(object me)


    {


        object ob;


        ob = this_object();





        if( !ob->query("embedded") || !me) return 0;





        if(environment(ob) != me


                || me->is_ghost()


                || !me->query_temp("embed")){


            delete("embedded");


            return 0;


        }





        me->receive_wound("qi", ob->query("damage")*2, "身中"+ob->name()+"而");


        me->receive_damage("qi", ob->query("damage")*4, "身中"+ob->name()+"而");


        tell_object(me, HIR "鲜血正顺着你身上的"+ob->name()+HIR"嘀嗒嘀嗒地直往下流！"NOR"\n" );


        tell_room(environment(me), HIR + "鲜血顺着"+me->name()+HIR"身上的"+ob->name()+HIR"直往下流！"NOR"\n", ({ me }));


        call_out("do_embed", 15+random(10), me);





        return 1;


    }*/


}
