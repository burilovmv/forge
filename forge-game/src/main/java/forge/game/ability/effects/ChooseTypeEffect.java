package forge.game.ability.effects;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import forge.card.CardType;
import forge.game.ability.AbilityUtils;
import forge.game.ability.SpellAbilityEffect;
import forge.game.card.Card;
import forge.game.player.Player;
import forge.game.spellability.SpellAbility;
import forge.game.spellability.TargetRestrictions;
import forge.util.Aggregates;

public class ChooseTypeEffect extends SpellAbilityEffect {

    @Override
    protected String getStackDescription(SpellAbility sa) {
        final StringBuilder sb = new StringBuilder();

        if (!sa.usesTargeting()) {
            for (final Player p : getTargetPlayers(sa)) {
                sb.append(p);
            }
            sb.append(" chooses a type.");
        } else {
            sb.append("Please improve the stack description.");
        }

        return sb.toString();
    }

    @Override
    public void resolve(SpellAbility sa) {
        final Card card = sa.getHostCard();
        final String type = sa.getParam("Type");
        final List<String> invalidTypes = sa.hasParam("InvalidTypes") ? Arrays.asList(sa.getParam("InvalidTypes").split(",")) : new ArrayList<>();
        final List<String> validTypes = new ArrayList<>();
        final List<Player> tgtPlayers = getTargetPlayers(sa);

        if (sa.hasParam("ValidTypes")) {
            validTypes.addAll(Arrays.asList(sa.getParam("ValidTypes").split(",")));
        }

        if (validTypes.isEmpty()) {
            switch (type) {
            case "Card":
                validTypes.addAll(CardType.getAllCardTypes());
                break;
            case "Creature":
                if (sa.hasParam("TypesFromDefined")) {
                    for (final Card c : AbilityUtils.getDefinedCards(card, sa.getParam("TypesFromDefined"), sa)) {
                        for (String t : c.getType()) {
                            if (CardType.isACreatureType(t)) {
                                validTypes.add(t);
                            }
                        }
                    }
                } else {
                    validTypes.addAll(CardType.getAllCreatureTypes());
                }
                break;
            case "Basic Land":
                validTypes.addAll(CardType.getBasicTypes());
                break;
            case "Land":
                validTypes.addAll(CardType.getAllLandTypes());
                break;
            case "CreatureInTargetedDeck":
                for (final Player p : tgtPlayers) {
                    for (Card c : p.getAllCards()) {
                        if (c.getType().getCreatureTypes() != null) {
                            for (String s : c.getType().getCreatureTypes()) {
                                if (!validTypes.contains(s)) {
                                    validTypes.add(s);
                                }
                            }
                        }
                    }
                }
            }
        }

        for (final String s : invalidTypes) {
            validTypes.remove(s);
        }
        if (sa.hasParam("Note") && card.hasAnyNotedType()) {
            for (String noted : card.getNotedTypes()) {
                validTypes.remove(noted);
            }
        }

        final TargetRestrictions tgt = sa.getTargetRestrictions();

        if (validTypes.isEmpty() && sa.hasParam("Note")) {
            // OK to end up with no choices/have nothing new to note
        } else if (!validTypes.isEmpty()) {
            for (final Player p : tgtPlayers) {
                String choice;
                if ((tgt == null) || p.canBeTargetedBy(sa)) {
                    Player noNotify = p;
                    if (sa.hasParam("AtRandom")) {
                        choice = Aggregates.random(validTypes);
                        noNotify = null;
                    } else {
                        choice = p.getController().chooseSomeType(type, sa, validTypes, invalidTypes);
                    }
                    if (sa.hasParam("Note")) {
                        card.addNotedType(choice);
                    } else if (!sa.hasParam("ChooseType2")) {
                        card.setChosenType(choice);
                    } else {
                        card.setChosenType2(choice);
                    }
                    p.getGame().getAction().notifyOfValue(sa, p, choice, noNotify);
                }
            }
        } else {
            throw new InvalidParameterException(sa.getHostCard() + "'s ability resulted in no types to choose from");
        }
    }
}
