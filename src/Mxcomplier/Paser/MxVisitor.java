// Generated from C:/Users/18617/IdeaProjects/Mx/src/Mxcomplier/Paser\Mx.g4 by ANTLR 4.8
package Mxcomplier.Paser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MxParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MxVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MxParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(MxParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#functiondec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctiondec(MxParser.FunctiondecContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stringtype}
	 * labeled alternative in {@link MxParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringtype(MxParser.StringtypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arraytype}
	 * labeled alternative in {@link MxParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArraytype(MxParser.ArraytypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code inttype}
	 * labeled alternative in {@link MxParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInttype(MxParser.InttypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code classtype}
	 * labeled alternative in {@link MxParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClasstype(MxParser.ClasstypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code voidtype}
	 * labeled alternative in {@link MxParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVoidtype(MxParser.VoidtypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code booltype}
	 * labeled alternative in {@link MxParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooltype(MxParser.BooltypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#parameterlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterlist(MxParser.ParameterlistContext ctx);
	/**
	 * Visit a parse tree produced by the {@code blockstmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockstmt(MxParser.BlockstmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code varstmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarstmt(MxParser.VarstmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ifstmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfstmt(MxParser.IfstmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code whilestmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhilestmt(MxParser.WhilestmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code forstmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForstmt(MxParser.ForstmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code retstmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRetstmt(MxParser.RetstmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code breakstmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakstmt(MxParser.BreakstmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code continuestmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinuestmt(MxParser.ContinuestmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprstmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprstmt(MxParser.ExprstmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nullstmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNullstmt(MxParser.NullstmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(MxParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code vardec1}
	 * labeled alternative in {@link MxParser#variabledec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVardec1(MxParser.Vardec1Context ctx);
	/**
	 * Visit a parse tree produced by the {@code vardec2}
	 * labeled alternative in {@link MxParser#variabledec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVardec2(MxParser.Vardec2Context ctx);
	/**
	 * Visit a parse tree produced by the {@code logicexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicexpr(MxParser.LogicexprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code indexexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexexpr(MxParser.IndexexprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stringexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringexpr(MxParser.StringexprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code prefixexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefixexpr(MxParser.PrefixexprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code bitexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBitexpr(MxParser.BitexprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code compexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompexpr(MxParser.CompexprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code addexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddexpr(MxParser.AddexprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code equalexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualexpr(MxParser.EqualexprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignexpr(MxParser.AssignexprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolexpr(MxParser.BoolexprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code shiftexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShiftexpr(MxParser.ShiftexprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code intexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntexpr(MxParser.IntexprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code thisexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThisexpr(MxParser.ThisexprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code constructexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructexpr(MxParser.ConstructexprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code dotexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDotexpr(MxParser.DotexprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code postfixexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfixexpr(MxParser.PostfixexprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code newexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewexpr(MxParser.NewexprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mulexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulexpr(MxParser.MulexprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code idenexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdenexpr(MxParser.IdenexprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nullexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNullexpr(MxParser.NullexprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code functionexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionexpr(MxParser.FunctionexprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code subexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubexpr(MxParser.SubexprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#classdec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassdec(MxParser.ClassdecContext ctx);
}