package com.expatrio.api.configuration.jooq

import org.jooq.ExecuteContext
import org.jooq.ExecuteListener
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator


class JooqToSpringExceptionConverter: ExecuteListener {
    override fun exception(ctx: ExecuteContext) {
        if( ctx.sqlException() == null) return
        val dialect = ctx.configuration().dialect()
        val translator = SQLErrorCodeSQLExceptionTranslator(dialect.name)
        ctx.exception(translator.translate("Access database using JOOQ", ctx.sql(), ctx.sqlException()!!))
    }
}