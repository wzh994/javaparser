/*
 * Copyright (C) 2007 Júlio Vilmar Gesser.
 * 
 * This file is part of Java 1.5 parser and Abstract Syntax Tree.
 *
 * Java 1.5 parser and Abstract Syntax Tree is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Java 1.5 parser and Abstract Syntax Tree is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Java 1.5 parser and Abstract Syntax Tree.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * Created on 04/11/2006
 */
package japa.parser.ast.stmt;

import japa.parser.ast.expr.Expression;
import japa.parser.ast.visitor.GenericVisitor;
import japa.parser.ast.visitor.VoidVisitor;

/**
 * @author Julio Vilmar Gesser
 */
public final class AssertStmt extends Statement {

	private Expression check;

	private Expression msg;

	public AssertStmt() {
	}

	public AssertStmt(final Expression check) {
		this.check = check;
	}

	public AssertStmt(final Expression check, final Expression msg) {
		this.check = check;
		this.msg = msg;
	}

	public AssertStmt(final int beginLine, final int beginColumn, final int endLine, final int endColumn,
			final Expression check, final Expression msg) {
		super(beginLine, beginColumn, endLine, endColumn);
		this.check = check;
		this.msg = msg;
	}

	@Override public <R, A> R accept(final GenericVisitor<R, A> v, final A arg) {
		return v.visit(this, arg);
	}

	@Override public <A> void accept(final VoidVisitor<A> v, final A arg) {
		v.visit(this, arg);
	}

	public Expression getCheck() {
		return check;
	}

	public Expression getMessage() {
		return msg;
	}

	public void setCheck(final Expression check) {
		this.check = check;
	}

	public void setMessage(final Expression msg) {
		this.msg = msg;
	}
}
