	.file	"builtin.c"
	.option nopic
	.text
	.align	2
	.globl	__malloc
	.type	__malloc, @function
__malloc:
	addi	sp,sp,-32
	sw	ra,28(sp)
	sw	s0,24(sp)
	addi	s0,sp,32
	sw	a0,-20(s0)
	lw	a5,-20(s0)
	mv	a0,a5
	call	malloc
	mv	a5,a0
	mv	a0,a5
	lw	ra,28(sp)
	lw	s0,24(sp)
	addi	sp,sp,32
	jr	ra
	.size	__malloc, .-__malloc
	.section	.rodata
	.align	2
.LC0:
	.string	"%s"
	.text
	.align	2
	.globl	__print
	.type	__print, @function
__print:
	addi	sp,sp,-32
	sw	ra,28(sp)
	sw	s0,24(sp)
	addi	s0,sp,32
	sw	a0,-20(s0)
	lw	a1,-20(s0)
	lui	a5,%hi(.LC0)
	addi	a0,a5,%lo(.LC0)
	call	printf
	nop
	lw	ra,28(sp)
	lw	s0,24(sp)
	addi	sp,sp,32
	jr	ra
	.size	__print, .-__print
	.align	2
	.globl	__println
	.type	__println, @function
__println:
	addi	sp,sp,-32
	sw	ra,28(sp)
	sw	s0,24(sp)
	addi	s0,sp,32
	sw	a0,-20(s0)
	lw	a0,-20(s0)
	call	puts
	nop
	lw	ra,28(sp)
	lw	s0,24(sp)
	addi	sp,sp,32
	jr	ra
	.size	__println, .-__println
	.section	.rodata
	.align	2
.LC1:
	.string	"%d"
	.text
	.align	2
	.globl	__printInt
	.type	__printInt, @function
__printInt:
	sw	ra,28(sp)
	sw	s0,24(sp)
	addi	s0,sp,32
	sw	a0,-20(s0)
	lw	a1,-20(s0)
	lui	a5,%hi(.LC1)
	addi	a0,a5,%lo(.LC1)
	call	printf
	nop
	lw	ra,28(sp)
	lw	s0,24(sp)
	jr	ra
	.size	__printInt, .-__printInt
	.section	.rodata
	.align	2
.LC2:
	.string	"%d\n"
	.text
	.align	2
	.globl	__printlnInt
	.type	__printlnInt, @function
__printlnInt:
	addi	sp,sp,-32
	sw	ra,28(sp)
	sw	s0,24(sp)
	addi	s0,sp,32
	sw	a0,-20(s0)
	lw	a1,-20(s0)
	lui	a5,%hi(.LC2)
	addi	a0,a5,%lo(.LC2)
	call	printf
	nop
	lw	ra,28(sp)
	lw	s0,24(sp)
	addi	sp,sp,32
	jr	ra
	.size	__printlnInt, .-__printlnInt
	.align	2
	.globl	__getString
	.type	__getString, @function
__getString:
	addi	sp,sp,-32
	sw	ra,28(sp)
	sw	s0,24(sp)
	addi	s0,sp,32
	li	a0,512
	call	malloc
	mv	a5,a0
	sw	a5,-20(s0)
	lw	a1,-20(s0)
	lui	a5,%hi(.LC0)
	addi	a0,a5,%lo(.LC0)
	call	scanf
	lw	a5,-20(s0)
	mv	a0,a5
	lw	ra,28(sp)
	lw	s0,24(sp)
	addi	sp,sp,32
	jr	ra
	.size	__getString, .-__getString
	.align	2
	.globl	__getInt
	.type	__getInt, @function
__getInt:
	sw	ra,28(sp)
	sw	s0,24(sp)
	addi	s0,sp,32
	addi	a5,s0,-20
	mv	a1,a5
	lui	a5,%hi(.LC1)
	addi	a0,a5,%lo(.LC1)
	call	scanf
	lw	a5,-20(s0)
	mv	a0,a5
	lw	ra,28(sp)
	lw	s0,24(sp)
	jr	ra
	.size	__getInt, .-__getInt
	.align	2
	.globl	__toString
	.type	__toString, @function
__toString:
	addi	sp,sp,-96
	sw	ra,92(sp)
	sw	s0,88(sp)
	addi	s0,sp,96
	sw	a0,-84(s0)
	sw	zero,-20(s0)
	sw	zero,-24(s0)
	lw	a5,-84(s0)
	bnez	a5,.L12
	lw	a5,-24(s0)
	addi	a4,a5,1
	sw	a4,-24(s0)
	slli	a5,a5,2
	addi	a4,s0,-16
	add	a5,a4,a5
	sw	zero,-56(a5)
.L12:
	lw	a5,-84(s0)
	bgez	a5,.L14
	li	a5,1
	sw	a5,-20(s0)
	lw	a5,-84(s0)
	sub	a5,zero,a5
	sw	a5,-84(s0)
	j	.L14
.L15:
	lw	a5,-24(s0)
	addi	a4,a5,1
	sw	a4,-24(s0)
	lw	a3,-84(s0)
	li	a4,10
	rem	a4,a3,a4
	slli	a5,a5,2
	addi	a3,s0,-16
	add	a5,a3,a5
	sw	a4,-56(a5)
	lw	a4,-84(s0)
	li	a5,10
	div	a5,a4,a5
	sw	a5,-84(s0)
.L14:
	lw	a5,-84(s0)
	bgtz	a5,.L15
	lw	a4,-20(s0)
	lw	a5,-24(s0)
	add	a5,a4,a5
	addi	a5,a5,1
	mv	a0,a5
	call	malloc
	mv	a5,a0
	sw	a5,-32(s0)
	sw	zero,-28(s0)
	lw	a5,-20(s0)
	beqz	a5,.L17
	lw	a5,-28(s0)
	addi	a4,a5,1
	sw	a4,-28(s0)
	mv	a4,a5
	lw	a5,-32(s0)
	add	a5,a5,a4
	li	a4,45
	sb	a4,0(a5)
	j	.L17
.L18:
	lw	a5,-24(s0)
	addi	a5,a5,-1
	sw	a5,-24(s0)
	lw	a5,-24(s0)
	slli	a5,a5,2
	addi	a4,s0,-16
	add	a5,a4,a5
	lw	a5,-56(a5)
	andi	a4,a5,0xff
	lw	a5,-28(s0)
	addi	a3,a5,1
	sw	a3,-28(s0)
	mv	a3,a5
	lw	a5,-32(s0)
	add	a5,a5,a3
	addi	a4,a4,48
	andi	a4,a4,0xff
	sb	a4,0(a5)
.L17:
	lw	a5,-24(s0)
	bgtz	a5,.L18
	lw	a5,-28(s0)
	addi	a4,a5,1
	sw	a4,-28(s0)
	mv	a4,a5
	lw	a5,-32(s0)
	add	a5,a5,a4
	sb	zero,0(a5)
	lw	a5,-32(s0)
	mv	a0,a5
	lw	ra,92(sp)
	lw	s0,88(sp)
	addi	sp,sp,96
	jr	ra
	.size	__toString, .-__toString
	.align	2
	.globl	__stringLength
	.type	__stringLength, @function
__stringLength:
	addi	sp,sp,-48
	sw	s0,44(sp)
	addi	s0,sp,48
	sw	a0,-36(s0)
	sw	zero,-20(s0)
	j	.L21
.L22:
	lw	a5,-20(s0)
	addi	a5,a5,1
	sw	a5,-20(s0)
.L21:
	lw	a5,-20(s0)
	lw	a4,-36(s0)
	add	a5,a4,a5
	lbu	a5,0(a5)
	bnez	a5,.L22
	lw	a5,-20(s0)
	mv	a0,a5
	lw	s0,44(sp)
	addi	sp,sp,48
	jr	ra
	.size	__stringLength, .-__stringLength
	.align	2
	.globl	__stringSubstring
	.type	__stringSubstring, @function
__stringSubstring:
	addi	sp,sp,-48
	sw	ra,44(sp)
	sw	s0,40(sp)
	addi	s0,sp,48
	sw	a0,-36(s0)
	sw	a1,-40(s0)
	sw	a2,-44(s0)
	lw	a4,-44(s0)
	lw	a5,-40(s0)
	sub	a5,a4,a5
	sw	a5,-24(s0)
	sw	zero,-20(s0)
	lw	a5,-24(s0)
	addi	a5,a5,1
	mv	a0,a5
	call	malloc
	mv	a5,a0
	sw	a5,-28(s0)
	sw	zero,-20(s0)
	j	.L25
.L26:
	lw	a4,-20(s0)
	lw	a5,-40(s0)
	add	a5,a4,a5
	mv	a4,a5
	lw	a5,-36(s0)
	add	a4,a5,a4
	lw	a5,-20(s0)
	lw	a3,-28(s0)
	add	a5,a3,a5
	lbu	a4,0(a4)
	sb	a4,0(a5)
	lw	a5,-20(s0)
	addi	a5,a5,1
	sw	a5,-20(s0)
.L25:
	lw	a4,-20(s0)
	lw	a5,-24(s0)
	blt	a4,a5,.L26
	lw	a5,-24(s0)
	lw	a4,-28(s0)
	add	a5,a4,a5
	sb	zero,0(a5)
	lw	a5,-28(s0)
	mv	a0,a5
	lw	ra,44(sp)
	lw	s0,40(sp)
	addi	sp,sp,48
	jr	ra
	.size	__stringSubstring, .-__stringSubstring
	.align	2
	.globl	__stringParseInt
	.type	__stringParseInt, @function
__stringParseInt:
	addi	sp,sp,-48
	sw	ra,44(sp)
	sw	s0,40(sp)
	addi	s0,sp,48
	sw	a0,-36(s0)
	addi	a5,s0,-20
	mv	a2,a5
	lui	a5,%hi(.LC1)
	addi	a1,a5,%lo(.LC1)
	lw	a0,-36(s0)
	call	sscanf
	lw	a5,-20(s0)
	mv	a0,a5
	lw	ra,44(sp)
	lw	s0,40(sp)
	addi	sp,sp,48
	jr	ra
	.size	__stringParseInt, .-__stringParseInt
	.align	2
	.globl	__stringOrd
	.type	__stringOrd, @function
__stringOrd:
	addi	sp,sp,-32
	sw	s0,28(sp)
	addi	s0,sp,32
	sw	a0,-20(s0)
	sw	a1,-24(s0)
	lw	a5,-24(s0)
	lw	a4,-20(s0)
	add	a5,a4,a5
	lbu	a5,0(a5)
	mv	a0,a5
	lw	s0,28(sp)
	addi	sp,sp,32
	jr	ra
	.size	__stringOrd, .-__stringOrd
	.align	2
	.globl	__arraySize
	.type	__arraySize, @function
__arraySize:
	addi	sp,sp,-32
	sw	s0,28(sp)
	addi	s0,sp,32
	sw	a0,-20(s0)
	lw	a5,-20(s0)
	lw	a5,-4(a5)
	mv	a0,a5
	lw	s0,28(sp)
	addi	sp,sp,32
	jr	ra
	.size	__arraySize, .-__arraySize
	.align	2
	.globl	__stringAdd
	.type	__stringAdd, @function
__stringAdd:
	addi	sp,sp,-64
	sw	ra,60(sp)
	sw	s0,56(sp)
	addi	s0,sp,64
	sw	a0,-52(s0)
	sw	a1,-56(s0)
	sw	zero,-20(s0)
	j	.L35
.L36:
	lw	a5,-20(s0)
	addi	a5,a5,1
	sw	a5,-20(s0)
.L35:
	lw	a5,-20(s0)
	lw	a4,-52(s0)
	add	a5,a4,a5
	lbu	a5,0(a5)
	bnez	a5,.L36
	sw	zero,-24(s0)
	j	.L37
.L38:
	lw	a5,-24(s0)
	addi	a5,a5,1
	sw	a5,-24(s0)
.L37:
	lw	a5,-24(s0)
	lw	a4,-56(s0)
	add	a5,a4,a5
	lbu	a5,0(a5)
	bnez	a5,.L38
	lw	a4,-20(s0)
	lw	a5,-24(s0)
	add	a5,a4,a5
	addi	a5,a5,1
	mv	a0,a5
	call	malloc
	mv	a5,a0
	sw	a5,-36(s0)
	sw	zero,-28(s0)
	j	.L39
.L40:
	lw	a5,-28(s0)
	lw	a4,-52(s0)
	add	a4,a4,a5
	lw	a5,-28(s0)
	lw	a3,-36(s0)
	add	a5,a3,a5
	lbu	a4,0(a4)
	sb	a4,0(a5)
	lw	a5,-28(s0)
	addi	a5,a5,1
	sw	a5,-28(s0)
.L39:
	lw	a4,-28(s0)
	lw	a5,-20(s0)
	blt	a4,a5,.L40
	sw	zero,-32(s0)
	j	.L41
.L42:
	lw	a5,-32(s0)
	lw	a4,-56(s0)
	add	a4,a4,a5
	lw	a3,-32(s0)
	lw	a5,-20(s0)
	add	a5,a3,a5
	mv	a3,a5
	lw	a5,-36(s0)
	add	a5,a5,a3
	lbu	a4,0(a4)
	sb	a4,0(a5)
	lw	a5,-32(s0)
	addi	a5,a5,1
	sw	a5,-32(s0)
.L41:
	lw	a4,-32(s0)
	lw	a5,-24(s0)
	blt	a4,a5,.L42
	lw	a4,-20(s0)
	lw	a5,-24(s0)
	add	a5,a4,a5
	mv	a4,a5
	lw	a5,-36(s0)
	add	a5,a5,a4
	sb	zero,0(a5)
	lw	a5,-36(s0)
	mv	a0,a5
	lw	ra,60(sp)
	lw	s0,56(sp)
	addi	sp,sp,64
	jr	ra
	.size	__stringAdd, .-__stringAdd
	.align	2
	.globl	__stringEqual
	.type	__stringEqual, @function
__stringEqual:
	addi	sp,sp,-32
	sw	ra,28(sp)
	sw	s0,24(sp)
	addi	s0,sp,32
	sw	a0,-20(s0)
	sw	a1,-24(s0)
	lw	a1,-24(s0)
	lw	a0,-20(s0)
	call	strcmp
	mv	a5,a0
	seqz	a5,a5
	andi	a5,a5,0xff
	mv	a0,a5
	lw	ra,28(sp)
	lw	s0,24(sp)
	addi	sp,sp,32
	jr	ra
	.size	__stringEqual, .-__stringEqual
	.align	2
	.globl	__stringNotEqual
	.type	__stringNotEqual, @function
__stringNotEqual:
	addi	sp,sp,-32
	sw	ra,28(sp)
	sw	s0,24(sp)
	addi	s0,sp,32
	sw	a0,-20(s0)
	sw	a1,-24(s0)
	lw	a1,-24(s0)
	lw	a0,-20(s0)
	call	strcmp
	mv	a5,a0
	snez	a5,a5
	andi	a5,a5,0xff
	mv	a0,a5
	lw	ra,28(sp)
	lw	s0,24(sp)
	addi	sp,sp,32
	jr	ra
	.size	__stringNotEqual, .-__stringNotEqual
	.align	2
	.globl	__stringLess
	.type	__stringLess, @function
__stringLess:
	addi	sp,sp,-32
	sw	ra,28(sp)
	sw	s0,24(sp)
	addi	s0,sp,32
	sw	a0,-20(s0)
	sw	a1,-24(s0)
	lw	a1,-24(s0)
	lw	a0,-20(s0)
	call	strcmp
	mv	a5,a0
	srli	a5,a5,31
	andi	a5,a5,0xff
	mv	a0,a5
	lw	ra,28(sp)
	lw	s0,24(sp)
	addi	sp,sp,32
	jr	ra
	.size	__stringLess, .-__stringLess
	.align	2
	.globl	__stringLessEqual
	.type	__stringLessEqual, @function
__stringLessEqual:
	addi	sp,sp,-32
	sw	ra,28(sp)
	sw	s0,24(sp)
	addi	s0,sp,32
	sw	a0,-20(s0)
	sw	a1,-24(s0)
	lw	a1,-24(s0)
	lw	a0,-20(s0)
	call	strcmp
	mv	a5,a0
	slti	a5,a5,1
	andi	a5,a5,0xff
	mv	a0,a5
	lw	ra,28(sp)
	lw	s0,24(sp)
	addi	sp,sp,32
	jr	ra
	.size	__stringLessEqual, .-__stringLessEqual
	.align	2
	.globl	__stringGreater
	.type	__stringGreater, @function
__stringGreater:
	addi	sp,sp,-32
	sw	ra,28(sp)
	sw	s0,24(sp)
	addi	s0,sp,32
	sw	a0,-20(s0)
	sw	a1,-24(s0)
	lw	a1,-24(s0)
	lw	a0,-20(s0)
	call	strcmp
	mv	a5,a0
	sgt	a5,a5,zero
	andi	a5,a5,0xff
	mv	a0,a5
	lw	ra,28(sp)
	lw	s0,24(sp)
	addi	sp,sp,32
	jr	ra
	.size	__stringGreater, .-__stringGreater
	.align	2
	.globl	__stringGreaterEqual
	.type	__stringGreaterEqual, @function
__stringGreaterEqual:
	addi	sp,sp,-32
	sw	ra,28(sp)
	sw	s0,24(sp)
	addi	s0,sp,32
	sw	a0,-20(s0)
	sw	a1,-24(s0)
	lw	a1,-24(s0)
	lw	a0,-20(s0)
	call	strcmp
	mv	a5,a0
	not	a5,a5
	srli	a5,a5,31
	andi	a5,a5,0xff
	mv	a0,a5
	lw	ra,28(sp)
	lw	s0,24(sp)
	addi	sp,sp,32
	jr	ra
	.size	__stringGreaterEqual, .-__stringGreaterEqual
	.ident	"GCC: (GNU) 8.2.0"