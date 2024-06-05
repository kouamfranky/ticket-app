package com.kouamfranky.ticketapp.models.enumerations;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 05/juin/2024 -- 04:38
 *
 * @author name :  Franky Brice on 05/06/2024
 * @author email : kouamfranky@gmail.com /  franky.kouam@iforce5.com
 * Project : @project ticket-app
 * Package : @package com.kouamfranky.ticketapp.models.enumerations
 **/
public enum StatutTicketEnum {
    ENCOURS(1,"En cours"),
    TERMINER(2,"terminé"),
    ANNULER(2,"annulé"),
    ;

    private final int cle;
    private final String valeur;

    StatutTicketEnum(int cle, String valeur) {
        this.cle = cle;
        this.valeur = valeur;
    }
}
