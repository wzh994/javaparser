/*
 * Copyright (C) 2008 Júlio Vilmar Gesser.
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
 * Created on 09/06/2008
 */
package japa.parser.ast.visitor;

import japa.parser.ast.BlockComment;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.LineComment;
import japa.parser.ast.PackageDeclaration;
import japa.parser.ast.TypeParameter;
import japa.parser.ast.body.AnnotationDeclaration;
import japa.parser.ast.body.AnnotationMemberDeclaration;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.EmptyMemberDeclaration;
import japa.parser.ast.body.EmptyTypeDeclaration;
import japa.parser.ast.body.EnumConstantDeclaration;
import japa.parser.ast.body.EnumDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.InitializerDeclaration;
import japa.parser.ast.body.JavadocComment;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.body.VariableDeclaratorId;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.expr.ArrayAccessExpr;
import japa.parser.ast.expr.ArrayCreationExpr;
import japa.parser.ast.expr.ArrayInitializerExpr;
import japa.parser.ast.expr.AssignExpr;
import japa.parser.ast.expr.BinaryExpr;
import japa.parser.ast.expr.BooleanLiteralExpr;
import japa.parser.ast.expr.CastExpr;
import japa.parser.ast.expr.CharLiteralExpr;
import japa.parser.ast.expr.ClassExpr;
import japa.parser.ast.expr.ConditionalExpr;
import japa.parser.ast.expr.DoubleLiteralExpr;
import japa.parser.ast.expr.EnclosedExpr;
import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.FieldAccessExpr;
import japa.parser.ast.expr.InstanceOfExpr;
import japa.parser.ast.expr.IntegerLiteralExpr;
import japa.parser.ast.expr.IntegerLiteralMinValueExpr;
import japa.parser.ast.expr.LongLiteralExpr;
import japa.parser.ast.expr.LongLiteralMinValueExpr;
import japa.parser.ast.expr.MarkerAnnotationExpr;
import japa.parser.ast.expr.MemberValuePair;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.expr.NormalAnnotationExpr;
import japa.parser.ast.expr.NullLiteralExpr;
import japa.parser.ast.expr.ObjectCreationExpr;
import japa.parser.ast.expr.QualifiedNameExpr;
import japa.parser.ast.expr.SingleMemberAnnotationExpr;
import japa.parser.ast.expr.StringLiteralExpr;
import japa.parser.ast.expr.SuperExpr;
import japa.parser.ast.expr.ThisExpr;
import japa.parser.ast.expr.UnaryExpr;
import japa.parser.ast.expr.VariableDeclarationExpr;
import japa.parser.ast.stmt.AssertStmt;
import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.stmt.BreakStmt;
import japa.parser.ast.stmt.CatchClause;
import japa.parser.ast.stmt.ContinueStmt;
import japa.parser.ast.stmt.DoStmt;
import japa.parser.ast.stmt.EmptyStmt;
import japa.parser.ast.stmt.ExplicitConstructorInvocationStmt;
import japa.parser.ast.stmt.ExpressionStmt;
import japa.parser.ast.stmt.ForStmt;
import japa.parser.ast.stmt.ForeachStmt;
import japa.parser.ast.stmt.IfStmt;
import japa.parser.ast.stmt.LabeledStmt;
import japa.parser.ast.stmt.ReturnStmt;
import japa.parser.ast.stmt.Statement;
import japa.parser.ast.stmt.SwitchEntryStmt;
import japa.parser.ast.stmt.SwitchStmt;
import japa.parser.ast.stmt.SynchronizedStmt;
import japa.parser.ast.stmt.ThrowStmt;
import japa.parser.ast.stmt.TryStmt;
import japa.parser.ast.stmt.TypeDeclarationStmt;
import japa.parser.ast.stmt.WhileStmt;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.type.PrimitiveType;
import japa.parser.ast.type.ReferenceType;
import japa.parser.ast.type.Type;
import japa.parser.ast.type.VoidType;
import japa.parser.ast.type.WildcardType;

/**
 * @author Julio Vilmar Gesser
 */
public abstract class GenericVisitorAdapter<R, A> implements GenericVisitor<R, A> {

	@Override public R visit(final AnnotationDeclaration n, final A arg) {
		if (n.getJavaDoc() != null) {
			n.getJavaDoc().accept(this, arg);
		}
		if (n.getAnnotations() != null) {
			for (final AnnotationExpr a : n.getAnnotations()) {
				a.accept(this, arg);
			}
		}
		if (n.getMembers() != null) {
			for (final BodyDeclaration member : n.getMembers()) {
				member.accept(this, arg);
			}
		}
		return null;
	}

	@Override public R visit(final AnnotationMemberDeclaration n, final A arg) {
		if (n.getJavaDoc() != null) {
			n.getJavaDoc().accept(this, arg);
		}
		if (n.getAnnotations() != null) {
			for (final AnnotationExpr a : n.getAnnotations()) {
				a.accept(this, arg);
			}
		}
		n.getType().accept(this, arg);
		if (n.getDefaultValue() != null) {
			n.getDefaultValue().accept(this, arg);
		}
		return null;
	}

	@Override public R visit(final ArrayAccessExpr n, final A arg) {
		n.getName().accept(this, arg);
		n.getIndex().accept(this, arg);
		return null;
	}

	@Override public R visit(final ArrayCreationExpr n, final A arg) {
		n.getType().accept(this, arg);
		if (n.getDimensions() != null) {
			for (final Expression dim : n.getDimensions()) {
				dim.accept(this, arg);
			}
		} else {
			n.getInitializer().accept(this, arg);
		}
		return null;
	}

	@Override public R visit(final ArrayInitializerExpr n, final A arg) {
		if (n.getValues() != null) {
			for (final Expression expr : n.getValues()) {
				expr.accept(this, arg);
			}
		}
		return null;
	}

	@Override public R visit(final AssertStmt n, final A arg) {
		n.getCheck().accept(this, arg);
		if (n.getMessage() != null) {
			n.getMessage().accept(this, arg);
		}
		return null;
	}

	@Override public R visit(final AssignExpr n, final A arg) {
		n.getTarget().accept(this, arg);
		n.getValue().accept(this, arg);
		return null;
	}

	@Override public R visit(final BinaryExpr n, final A arg) {
		n.getLeft().accept(this, arg);
		n.getRight().accept(this, arg);
		return null;
	}

	@Override public R visit(final BlockStmt n, final A arg) {
		if (n.getStmts() != null) {
			for (final Statement s : n.getStmts()) {
				s.accept(this, arg);
			}
		}
		return null;

	}

	@Override public R visit(final BooleanLiteralExpr n, final A arg) {
		return null;
	}

	@Override public R visit(final BreakStmt n, final A arg) {
		return null;
	}

	@Override public R visit(final CastExpr n, final A arg) {
		n.getType().accept(this, arg);
		n.getExpr().accept(this, arg);
		return null;
	}

	@Override public R visit(final CatchClause n, final A arg) {
		n.getExcept().accept(this, arg);
		n.getCatchBlock().accept(this, arg);
		return null;

	}

	@Override public R visit(final CharLiteralExpr n, final A arg) {
		return null;
	}

	@Override public R visit(final ClassExpr n, final A arg) {
		n.getType().accept(this, arg);
		return null;
	}

	@Override public R visit(final ClassOrInterfaceDeclaration n, final A arg) {
		if (n.getJavaDoc() != null) {
			n.getJavaDoc().accept(this, arg);
		}
		if (n.getAnnotations() != null) {
			for (final AnnotationExpr a : n.getAnnotations()) {
				a.accept(this, arg);
			}
		}
		if (n.getTypeParameters() != null) {
			for (final TypeParameter t : n.getTypeParameters()) {
				t.accept(this, arg);
			}
		}
		if (n.getExtends() != null) {
			for (final ClassOrInterfaceType c : n.getExtends()) {
				c.accept(this, arg);
			}
		}

		if (n.getImplements() != null) {
			for (final ClassOrInterfaceType c : n.getImplements()) {
				c.accept(this, arg);
			}
		}
		if (n.getMembers() != null) {
			for (final BodyDeclaration member : n.getMembers()) {
				member.accept(this, arg);
			}
		}
		return null;
	}

	@Override public R visit(final ClassOrInterfaceType n, final A arg) {
		if (n.getScope() != null) {
			n.getScope().accept(this, arg);
		}
		if (n.getTypeArgs() != null) {
			for (final Type t : n.getTypeArgs()) {
				t.accept(this, arg);
			}
		}
		return null;
	}

	@Override public R visit(final CompilationUnit n, final A arg) {
		if (n.getPackage() != null) {
			n.getPackage().accept(this, arg);
		}
		if (n.getImports() != null) {
			for (final ImportDeclaration i : n.getImports()) {
				i.accept(this, arg);
			}
		}
		if (n.getTypes() != null) {
			for (final TypeDeclaration typeDeclaration : n.getTypes()) {
				typeDeclaration.accept(this, arg);
			}
		}
		return null;
	}

	@Override public R visit(final ConditionalExpr n, final A arg) {
		n.getCondition().accept(this, arg);
		n.getThenExpr().accept(this, arg);
		n.getElseExpr().accept(this, arg);
		return null;
	}

	@Override public R visit(final ConstructorDeclaration n, final A arg) {
		if (n.getJavaDoc() != null) {
			n.getJavaDoc().accept(this, arg);
		}
		if (n.getAnnotations() != null) {
			for (final AnnotationExpr a : n.getAnnotations()) {
				a.accept(this, arg);
			}
		}
		if (n.getTypeParameters() != null) {
			for (final TypeParameter t : n.getTypeParameters()) {
				t.accept(this, arg);
			}
		}
		if (n.getParameters() != null) {
			for (final Parameter p : n.getParameters()) {
				p.accept(this, arg);
			}
		}
		if (n.getThrows() != null) {
			for (final NameExpr name : n.getThrows()) {
				name.accept(this, arg);
			}
		}
		n.getBlock().accept(this, arg);
		return null;
	}

	@Override public R visit(final ContinueStmt n, final A arg) {
		return null;
	}

	@Override public R visit(final DoStmt n, final A arg) {
		n.getBody().accept(this, arg);
		n.getCondition().accept(this, arg);
		return null;
	}

	@Override public R visit(final DoubleLiteralExpr n, final A arg) {
		return null;
	}

	@Override public R visit(final EmptyMemberDeclaration n, final A arg) {
		if (n.getJavaDoc() != null) {
			n.getJavaDoc().accept(this, arg);
		}
		return null;
	}

	@Override public R visit(final EmptyStmt n, final A arg) {
		return null;
	}

	@Override public R visit(final EmptyTypeDeclaration n, final A arg) {
		if (n.getJavaDoc() != null) {
			n.getJavaDoc().accept(this, arg);
		}
		return null;
	}

	@Override public R visit(final EnclosedExpr n, final A arg) {
		n.getInner().accept(this, arg);
		return null;
	}

	@Override public R visit(final EnumConstantDeclaration n, final A arg) {
		if (n.getJavaDoc() != null) {
			n.getJavaDoc().accept(this, arg);
		}
		if (n.getAnnotations() != null) {
			for (final AnnotationExpr a : n.getAnnotations()) {
				a.accept(this, arg);
			}
		}
		if (n.getArgs() != null) {
			for (final Expression e : n.getArgs()) {
				e.accept(this, arg);
			}
		}
		if (n.getClassBody() != null) {
			for (final BodyDeclaration member : n.getClassBody()) {
				member.accept(this, arg);
			}
		}
		return null;
	}

	@Override public R visit(final EnumDeclaration n, final A arg) {
		if (n.getJavaDoc() != null) {
			n.getJavaDoc().accept(this, arg);
		}
		if (n.getAnnotations() != null) {
			for (final AnnotationExpr a : n.getAnnotations()) {
				a.accept(this, arg);
			}
		}
		if (n.getImplements() != null) {
			for (final ClassOrInterfaceType c : n.getImplements()) {
				c.accept(this, arg);
			}
		}
		if (n.getEntries() != null) {
			for (final EnumConstantDeclaration e : n.getEntries()) {
				e.accept(this, arg);
			}
		}
		if (n.getMembers() != null) {
			for (final BodyDeclaration member : n.getMembers()) {
				member.accept(this, arg);
			}
		}
		return null;
	}

	@Override public R visit(final ExplicitConstructorInvocationStmt n, final A arg) {
		if (!n.isThis()) {
			if (n.getExpr() != null) {
				n.getExpr().accept(this, arg);
			}
		}
		if (n.getTypeArgs() != null) {
			for (final Type t : n.getTypeArgs()) {
				t.accept(this, arg);
			}
		}
		if (n.getArgs() != null) {
			for (final Expression e : n.getArgs()) {
				e.accept(this, arg);
			}
		}
		return null;
	}

	@Override public R visit(final ExpressionStmt n, final A arg) {
		n.getExpression().accept(this, arg);
		return null;
	}

	@Override public R visit(final FieldAccessExpr n, final A arg) {
		n.getScope().accept(this, arg);
		return null;
	}

	@Override public R visit(final FieldDeclaration n, final A arg) {
		if (n.getJavaDoc() != null) {
			n.getJavaDoc().accept(this, arg);
		}
		if (n.getAnnotations() != null) {
			for (final AnnotationExpr a : n.getAnnotations()) {
				a.accept(this, arg);
			}
		}
		n.getType().accept(this, arg);
		for (final VariableDeclarator var : n.getVariables()) {
			var.accept(this, arg);
		}
		return null;
	}

	@Override public R visit(final ForeachStmt n, final A arg) {
		n.getVariable().accept(this, arg);
		n.getIterable().accept(this, arg);
		n.getBody().accept(this, arg);
		return null;
	}

	@Override public R visit(final ForStmt n, final A arg) {
		if (n.getInit() != null) {
			for (final Expression e : n.getInit()) {
				e.accept(this, arg);
			}
		}
		if (n.getCompare() != null) {
			n.getCompare().accept(this, arg);
		}
		if (n.getUpdate() != null) {
			for (final Expression e : n.getUpdate()) {
				e.accept(this, arg);
			}
		}
		n.getBody().accept(this, arg);
		return null;
	}

	@Override public R visit(final IfStmt n, final A arg) {
		n.getCondition().accept(this, arg);
		n.getThenStmt().accept(this, arg);
		if (n.getElseStmt() != null) {
			n.getElseStmt().accept(this, arg);
		}
		return null;
	}

	@Override public R visit(final ImportDeclaration n, final A arg) {
		n.getName().accept(this, arg);
		return null;
	}

	@Override public R visit(final InitializerDeclaration n, final A arg) {
		if (n.getJavaDoc() != null) {
			n.getJavaDoc().accept(this, arg);
		}
		n.getBlock().accept(this, arg);
		return null;
	}

	@Override public R visit(final InstanceOfExpr n, final A arg) {
		n.getExpr().accept(this, arg);
		n.getType().accept(this, arg);
		return null;
	}

	@Override public R visit(final IntegerLiteralExpr n, final A arg) {
		return null;
	}

	@Override public R visit(final IntegerLiteralMinValueExpr n, final A arg) {
		return null;
	}

	@Override public R visit(final JavadocComment n, final A arg) {
		return null;
	}

	@Override public R visit(final LabeledStmt n, final A arg) {
		n.getStmt().accept(this, arg);
		return null;
	}

	@Override public R visit(final LongLiteralExpr n, final A arg) {
		return null;
	}

	@Override public R visit(final LongLiteralMinValueExpr n, final A arg) {
		return null;
	}

	@Override public R visit(final MarkerAnnotationExpr n, final A arg) {
		n.getName().accept(this, arg);
		return null;
	}

	@Override public R visit(final MemberValuePair n, final A arg) {
		n.getValue().accept(this, arg);
		return null;
	}

	@Override public R visit(final MethodCallExpr n, final A arg) {
		if (n.getScope() != null) {
			n.getScope().accept(this, arg);
		}
		if (n.getTypeArgs() != null) {
			for (final Type t : n.getTypeArgs()) {
				t.accept(this, arg);
			}
		}
		if (n.getArgs() != null) {
			for (final Expression e : n.getArgs()) {
				e.accept(this, arg);
			}
		}
		return null;
	}

	@Override public R visit(final MethodDeclaration n, final A arg) {
		if (n.getJavaDoc() != null) {
			n.getJavaDoc().accept(this, arg);
		}
		if (n.getAnnotations() != null) {
			for (final AnnotationExpr a : n.getAnnotations()) {
				a.accept(this, arg);
			}
		}
		if (n.getTypeParameters() != null) {
			for (final TypeParameter t : n.getTypeParameters()) {
				t.accept(this, arg);
			}
		}
		n.getType().accept(this, arg);
		if (n.getParameters() != null) {
			for (final Parameter p : n.getParameters()) {
				p.accept(this, arg);
			}
		}
		if (n.getThrows() != null) {
			for (final NameExpr name : n.getThrows()) {
				name.accept(this, arg);
			}
		}
		if (n.getBody() != null) {
			n.getBody().accept(this, arg);
		}
		return null;
	}

	@Override public R visit(final NameExpr n, final A arg) {
		return null;
	}

	@Override public R visit(final NormalAnnotationExpr n, final A arg) {
		n.getName().accept(this, arg);
		if (n.getPairs() != null) {
			for (final MemberValuePair m : n.getPairs()) {
				m.accept(this, arg);
			}
		}
		return null;
	}

	@Override public R visit(final NullLiteralExpr n, final A arg) {
		return null;
	}

	@Override public R visit(final ObjectCreationExpr n, final A arg) {
		if (n.getScope() != null) {
			n.getScope().accept(this, arg);
		}
		if (n.getTypeArgs() != null) {
			for (final Type t : n.getTypeArgs()) {
				t.accept(this, arg);
			}
		}
		n.getType().accept(this, arg);
		if (n.getArgs() != null) {
			for (final Expression e : n.getArgs()) {
				e.accept(this, arg);
			}
		}
		if (n.getAnonymousClassBody() != null) {
			for (final BodyDeclaration member : n.getAnonymousClassBody()) {
				member.accept(this, arg);
			}
		}
		return null;
	}

	@Override public R visit(final PackageDeclaration n, final A arg) {
		if (n.getAnnotations() != null) {
			for (final AnnotationExpr a : n.getAnnotations()) {
				a.accept(this, arg);
			}
		}
		n.getName().accept(this, arg);
		return null;
	}

	@Override public R visit(final Parameter n, final A arg) {
		if (n.getAnnotations() != null) {
			for (final AnnotationExpr a : n.getAnnotations()) {
				a.accept(this, arg);
			}
		}
		n.getType().accept(this, arg);
		n.getId().accept(this, arg);
		return null;
	}

	@Override public R visit(final PrimitiveType n, final A arg) {
		return null;
	}

	@Override public R visit(final QualifiedNameExpr n, final A arg) {
		n.getQualifier().accept(this, arg);
		return null;
	}

	@Override public R visit(final ReferenceType n, final A arg) {
		n.getType().accept(this, arg);
		return null;
	}

	@Override public R visit(final ReturnStmt n, final A arg) {
		if (n.getExpr() != null) {
			n.getExpr().accept(this, arg);
		}
		return null;
	}

	@Override public R visit(final SingleMemberAnnotationExpr n, final A arg) {
		n.getName().accept(this, arg);
		n.getMemberValue().accept(this, arg);
		return null;
	}

	@Override public R visit(final StringLiteralExpr n, final A arg) {
		return null;
	}

	@Override public R visit(final SuperExpr n, final A arg) {
		if (n.getClassExpr() != null) {
			n.getClassExpr().accept(this, arg);
		}
		return null;
	}

	@Override public R visit(final SwitchEntryStmt n, final A arg) {
		if (n.getLabel() != null) {
			n.getLabel().accept(this, arg);
		}
		if (n.getStmts() != null) {
			for (final Statement s : n.getStmts()) {
				s.accept(this, arg);
			}
		}
		return null;
	}

	@Override public R visit(final SwitchStmt n, final A arg) {
		n.getSelector().accept(this, arg);
		if (n.getEntries() != null) {
			for (final SwitchEntryStmt e : n.getEntries()) {
				e.accept(this, arg);
			}
		}
		return null;

	}

	@Override public R visit(final SynchronizedStmt n, final A arg) {
		n.getExpr().accept(this, arg);
		n.getBlock().accept(this, arg);
		return null;
	}

	@Override public R visit(final ThisExpr n, final A arg) {
		if (n.getClassExpr() != null) {
			n.getClassExpr().accept(this, arg);
		}
		return null;
	}

	@Override public R visit(final ThrowStmt n, final A arg) {
		n.getExpr().accept(this, arg);
		return null;
	}

	@Override public R visit(final TryStmt n, final A arg) {
		n.getTryBlock().accept(this, arg);
		if (n.getCatchs() != null) {
			for (final CatchClause c : n.getCatchs()) {
				c.accept(this, arg);
			}
		}
		if (n.getFinallyBlock() != null) {
			n.getFinallyBlock().accept(this, arg);
		}
		return null;
	}

	@Override public R visit(final TypeDeclarationStmt n, final A arg) {
		n.getTypeDeclaration().accept(this, arg);
		return null;
	}

	@Override public R visit(final TypeParameter n, final A arg) {
		if (n.getTypeBound() != null) {
			for (final ClassOrInterfaceType c : n.getTypeBound()) {
				c.accept(this, arg);
			}
		}
		return null;
	}

	@Override public R visit(final UnaryExpr n, final A arg) {
		n.getExpr().accept(this, arg);
		return null;
	}

	@Override public R visit(final VariableDeclarationExpr n, final A arg) {
		if (n.getAnnotations() != null) {
			for (final AnnotationExpr a : n.getAnnotations()) {
				a.accept(this, arg);
			}
		}
		n.getType().accept(this, arg);
		for (final VariableDeclarator v : n.getVars()) {
			v.accept(this, arg);
		}
		return null;
	}

	@Override public R visit(final VariableDeclarator n, final A arg) {
		n.getId().accept(this, arg);
		if (n.getInit() != null) {
			n.getInit().accept(this, arg);
		}
		return null;
	}

	@Override public R visit(final VariableDeclaratorId n, final A arg) {
		return null;
	}

	@Override public R visit(final VoidType n, final A arg) {
		return null;
	}

	@Override public R visit(final WhileStmt n, final A arg) {
		n.getCondition().accept(this, arg);
		n.getBody().accept(this, arg);
		return null;
	}

	@Override public R visit(final WildcardType n, final A arg) {
		if (n.getExtends() != null) {
			n.getExtends().accept(this, arg);
		}
		if (n.getSuper() != null) {
			n.getSuper().accept(this, arg);
		}
		return null;
	}

	@Override public R visit(final BlockComment n, final A arg) {
		return null;
	}

	@Override public R visit(final LineComment n, final A arg) {
		return null;
	}

}
