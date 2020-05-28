// Generated from C:/Users/18617/IdeaProjects/Mx/src/Mxcomplier/Paser\Mx.g4 by ANTLR 4.8
package Mxcomplier.Paser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MxParser}.
 */
public interface MxListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MxParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(MxParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(MxParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#functiondec}.
	 * @param ctx the parse tree
	 */
	void enterFunctiondec(MxParser.FunctiondecContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#functiondec}.
	 * @param ctx the parse tree
	 */
	void exitFunctiondec(MxParser.FunctiondecContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stringtype}
	 * labeled alternative in {@link MxParser#type}.
	 * @param ctx the parse tree
	 */
	void enterStringtype(MxParser.StringtypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stringtype}
	 * labeled alternative in {@link MxParser#type}.
	 * @param ctx the parse tree
	 */
	void exitStringtype(MxParser.StringtypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arraytype}
	 * labeled alternative in {@link MxParser#type}.
	 * @param ctx the parse tree
	 */
	void enterArraytype(MxParser.ArraytypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arraytype}
	 * labeled alternative in {@link MxParser#type}.
	 * @param ctx the parse tree
	 */
	void exitArraytype(MxParser.ArraytypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code inttype}
	 * labeled alternative in {@link MxParser#type}.
	 * @param ctx the parse tree
	 */
	void enterInttype(MxParser.InttypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code inttype}
	 * labeled alternative in {@link MxParser#type}.
	 * @param ctx the parse tree
	 */
	void exitInttype(MxParser.InttypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code classtype}
	 * labeled alternative in {@link MxParser#type}.
	 * @param ctx the parse tree
	 */
	void enterClasstype(MxParser.ClasstypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code classtype}
	 * labeled alternative in {@link MxParser#type}.
	 * @param ctx the parse tree
	 */
	void exitClasstype(MxParser.ClasstypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code voidtype}
	 * labeled alternative in {@link MxParser#type}.
	 * @param ctx the parse tree
	 */
	void enterVoidtype(MxParser.VoidtypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code voidtype}
	 * labeled alternative in {@link MxParser#type}.
	 * @param ctx the parse tree
	 */
	void exitVoidtype(MxParser.VoidtypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code booltype}
	 * labeled alternative in {@link MxParser#type}.
	 * @param ctx the parse tree
	 */
	void enterBooltype(MxParser.BooltypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code booltype}
	 * labeled alternative in {@link MxParser#type}.
	 * @param ctx the parse tree
	 */
	void exitBooltype(MxParser.BooltypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#parameterlist}.
	 * @param ctx the parse tree
	 */
	void enterParameterlist(MxParser.ParameterlistContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#parameterlist}.
	 * @param ctx the parse tree
	 */
	void exitParameterlist(MxParser.ParameterlistContext ctx);
	/**
	 * Enter a parse tree produced by the {@code blockstmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterBlockstmt(MxParser.BlockstmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code blockstmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitBlockstmt(MxParser.BlockstmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code varstmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterVarstmt(MxParser.VarstmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code varstmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitVarstmt(MxParser.VarstmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ifstmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterIfstmt(MxParser.IfstmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ifstmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitIfstmt(MxParser.IfstmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code whilestmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterWhilestmt(MxParser.WhilestmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code whilestmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitWhilestmt(MxParser.WhilestmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code forstmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterForstmt(MxParser.ForstmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code forstmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitForstmt(MxParser.ForstmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code retstmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterRetstmt(MxParser.RetstmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code retstmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitRetstmt(MxParser.RetstmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code breakstmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterBreakstmt(MxParser.BreakstmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code breakstmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitBreakstmt(MxParser.BreakstmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code continuestmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterContinuestmt(MxParser.ContinuestmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code continuestmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitContinuestmt(MxParser.ContinuestmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprstmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterExprstmt(MxParser.ExprstmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprstmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitExprstmt(MxParser.ExprstmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nullstmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterNullstmt(MxParser.NullstmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nullstmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitNullstmt(MxParser.NullstmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(MxParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(MxParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code vardec1}
	 * labeled alternative in {@link MxParser#variabledec}.
	 * @param ctx the parse tree
	 */
	void enterVardec1(MxParser.Vardec1Context ctx);
	/**
	 * Exit a parse tree produced by the {@code vardec1}
	 * labeled alternative in {@link MxParser#variabledec}.
	 * @param ctx the parse tree
	 */
	void exitVardec1(MxParser.Vardec1Context ctx);
	/**
	 * Enter a parse tree produced by the {@code vardec2}
	 * labeled alternative in {@link MxParser#variabledec}.
	 * @param ctx the parse tree
	 */
	void enterVardec2(MxParser.Vardec2Context ctx);
	/**
	 * Exit a parse tree produced by the {@code vardec2}
	 * labeled alternative in {@link MxParser#variabledec}.
	 * @param ctx the parse tree
	 */
	void exitVardec2(MxParser.Vardec2Context ctx);
	/**
	 * Enter a parse tree produced by the {@code logicexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLogicexpr(MxParser.LogicexprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logicexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLogicexpr(MxParser.LogicexprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code indexexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIndexexpr(MxParser.IndexexprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code indexexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIndexexpr(MxParser.IndexexprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stringexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterStringexpr(MxParser.StringexprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stringexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitStringexpr(MxParser.StringexprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code prefixexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPrefixexpr(MxParser.PrefixexprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code prefixexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPrefixexpr(MxParser.PrefixexprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code bitexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBitexpr(MxParser.BitexprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code bitexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBitexpr(MxParser.BitexprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code compexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterCompexpr(MxParser.CompexprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code compexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitCompexpr(MxParser.CompexprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code addexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAddexpr(MxParser.AddexprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code addexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAddexpr(MxParser.AddexprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code equalexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterEqualexpr(MxParser.EqualexprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code equalexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitEqualexpr(MxParser.EqualexprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAssignexpr(MxParser.AssignexprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAssignexpr(MxParser.AssignexprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBoolexpr(MxParser.BoolexprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBoolexpr(MxParser.BoolexprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code shiftexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterShiftexpr(MxParser.ShiftexprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code shiftexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitShiftexpr(MxParser.ShiftexprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIntexpr(MxParser.IntexprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIntexpr(MxParser.IntexprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code thisexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterThisexpr(MxParser.ThisexprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code thisexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitThisexpr(MxParser.ThisexprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code constructexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterConstructexpr(MxParser.ConstructexprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code constructexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitConstructexpr(MxParser.ConstructexprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code dotexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterDotexpr(MxParser.DotexprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code dotexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitDotexpr(MxParser.DotexprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code postfixexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPostfixexpr(MxParser.PostfixexprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code postfixexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPostfixexpr(MxParser.PostfixexprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code newexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNewexpr(MxParser.NewexprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code newexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNewexpr(MxParser.NewexprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code mulexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMulexpr(MxParser.MulexprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code mulexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMulexpr(MxParser.MulexprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code idenexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIdenexpr(MxParser.IdenexprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code idenexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIdenexpr(MxParser.IdenexprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nullexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNullexpr(MxParser.NullexprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nullexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNullexpr(MxParser.NullexprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code functionexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterFunctionexpr(MxParser.FunctionexprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code functionexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitFunctionexpr(MxParser.FunctionexprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code subexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSubexpr(MxParser.SubexprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code subexpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSubexpr(MxParser.SubexprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#classdec}.
	 * @param ctx the parse tree
	 */
	void enterClassdec(MxParser.ClassdecContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#classdec}.
	 * @param ctx the parse tree
	 */
	void exitClassdec(MxParser.ClassdecContext ctx);
}