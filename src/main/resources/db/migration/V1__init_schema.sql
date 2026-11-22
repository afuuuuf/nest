
-- ─── users ──────────────────────────────────────────────────────────────────
CREATE TABLE users (
    id            UUID      NOT NULL,
    email         VARCHAR   NOT NULL,
    password_hash VARCHAR   NOT NULL,
    username      VARCHAR   NOT NULL,
    first_name    VARCHAR   NOT NULL,
    last_name     VARCHAR   NOT NULL,
    created_at    TIMESTAMP NOT NULL,
    updated_at    TIMESTAMP,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT users_email_key UNIQUE (email)
);

-- ─── budgets ────────────────────────────────────────────────────────────────
CREATE TABLE budgets (
    id            UUID        NOT NULL,
    user_id       UUID        NOT NULL,
    amount        BIGINT      NOT NULL,
    category      VARCHAR(20) NOT NULL,
    created_at    TIMESTAMP   NOT NULL,
    created_month VARCHAR     NOT NULL,
    updated_at    TIMESTAMP,
    deleted_at    TIMESTAMP,
    CONSTRAINT budgets_pkey PRIMARY KEY (id),
    CONSTRAINT budgets_user_id_fkey FOREIGN KEY (user_id)
        REFERENCES users (id) ON DELETE CASCADE
);

CREATE INDEX ix_budgets_category ON budgets (category);

-- ─── transactions ───────────────────────────────────────────────────────────
CREATE TABLE transactions (
    id               UUID        NOT NULL,
    user_id          UUID        NOT NULL,
    description      VARCHAR     NOT NULL,
    amount           BIGINT      NOT NULL,
    category         VARCHAR(20) NOT NULL,
    transaction_flow VARCHAR(20) NOT NULL,
    created_at       TIMESTAMP   NOT NULL,
    updated_at       TIMESTAMP,
    deleted_at       TIMESTAMP,
    CONSTRAINT transactions_pkey PRIMARY KEY (id),
    CONSTRAINT transactions_user_id_fkey FOREIGN KEY (user_id)
        REFERENCES users (id) ON DELETE CASCADE
);

CREATE INDEX ix_transactions_category ON transactions (category);

-- ─── savings_goals ──────────────────────────────────────────────────────────
CREATE TABLE savings_goals (
    id            UUID         NOT NULL,
    user_id       UUID         NOT NULL,
    name          VARCHAR(100) NOT NULL,
    target_amount BIGINT       NOT NULL,
    target_date   DATE,
    status        VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE',
    icon          VARCHAR(50),
    color         VARCHAR(7),
    created_at    TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at    TIMESTAMPTZ  NOT NULL DEFAULT now(),
    deleted_at    TIMESTAMPTZ,
    CONSTRAINT savings_goals_pkey PRIMARY KEY (id),
    CONSTRAINT savings_goals_user_id_fkey FOREIGN KEY (user_id)
        REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT target_positive CHECK (target_amount > 0)
);

CREATE INDEX ix_savings_goals_user ON savings_goals (user_id)
    WHERE deleted_at IS NULL;

-- ─── savings_contributions ──────────────────────────────────────────────────
CREATE TABLE savings_contributions (
    id             UUID         NOT NULL,
    goal_id        UUID         NOT NULL,
    user_id        UUID         NOT NULL,
    amount         BIGINT       NOT NULL,
    contributed_at DATE         NOT NULL,
    contributed_by VARCHAR(255),
    note           VARCHAR(255),
    created_at     TIMESTAMPTZ  NOT NULL DEFAULT now(),
    CONSTRAINT savings_contributions_pkey PRIMARY KEY (id),
    CONSTRAINT savings_contributions_goal_id_fkey FOREIGN KEY (goal_id)
        REFERENCES savings_goals (id) ON DELETE CASCADE,
    CONSTRAINT savings_contributions_user_id_fkey FOREIGN KEY (user_id)
        REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT amount_nonzero CHECK (amount <> 0)
);

CREATE INDEX ix_contrib_goal_date ON savings_contributions (goal_id, contributed_at);
