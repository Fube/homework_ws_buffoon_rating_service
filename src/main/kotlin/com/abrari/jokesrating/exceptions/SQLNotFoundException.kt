package com.abrari.jokesrating.exceptions

import java.sql.SQLException

class SQLNotFoundException(msg:String): SQLException(msg)