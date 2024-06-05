package com.kouamfranky.ticketapp.models.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kouamfranky.ticketapp.models.enumerations.StatutTicketEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 05/juin/2024 -- 04:06
 *
 * @author name :  Franky Brice on 05/06/2024
 * @author email : kouamfranky@gmail.com /  franky.kouam@iforce5.com
 * Project : @project ticket-app
 * Package : @package com.kouamfranky.ticketapp.models.entities
 **/
@Entity
@Getter
@Setter
@Table(name = "APP_TICKET")
public class Ticket extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    private String description;

    @Enumerated(EnumType.ORDINAL)
    private StatutTicketEnum statut;

    @JoinColumn(name = "id_user", foreignKey = @ForeignKey(name = "FK_APP_USER_ON_APP_TICKET"))
    @ManyToOne(targetEntity = User.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private User user;

    public static final class TicketBuilder {
        private Long id;
        private String titre;
        private String description;
        private StatutTicketEnum statut;

        private TicketBuilder() {
        }

        public static TicketBuilder aTicket() {
            return new TicketBuilder();
        }

        public TicketBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public TicketBuilder titre(String titre) {
            this.titre = titre;
            return this;
        }

        public TicketBuilder description(String description) {
            this.description = description;
            return this;
        }

        public TicketBuilder statut(StatutTicketEnum statut) {
            this.statut = statut;
            return this;
        }

        public Ticket build() {
            Ticket ticket = new Ticket();
            ticket.setId(id);
            ticket.setTitre(titre);
            ticket.setDescription(description);
            ticket.setStatut(statut);
            return ticket;
        }
    }
}
