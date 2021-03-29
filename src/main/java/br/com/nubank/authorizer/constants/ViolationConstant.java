package br.com.nubank.authorizer.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 *   Constants of violation messages.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ViolationConstant {
    public static final String ACCOUNT_ALREADY_INITIALIZED_MESSAGE      = "account-already-initialized";
    public static final String ACCOUNT_NOT_INITIALIZED_MESSAGE          = "account-not-initialized";
    public static final String CARD_NOT_ACTIVE_MESSAGE                  = "card-not-active";
    public static final String HIGH_FREQUENCY_SMALL_INTERVAL_MESSAGE    = "high-frequency-small-interval";
    public static final String INSUFFICIENT_LIMIT_MESSAGE               = "insufficient-limit";
    public static final String DOUBLED_TRANSACTION_MESSAGE              = "doubled-transaction";
}
