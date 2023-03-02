/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicatest;

import model.ExaminationRoom;

/**
 *
 * @author Equipo
 */
public class Habitacio {
    ExaminationRoom h;
    
    public Habitacio(ExaminationRoom h){ this.h = h;}
    @Override
    public String toString(){
        return "Sala "+h.getIdentNumber()/*+" ("+h.getEquipmentDescription()+")"*/;
    }
    public ExaminationRoom examinationRoomAssociated(){ return h;}
    
}
