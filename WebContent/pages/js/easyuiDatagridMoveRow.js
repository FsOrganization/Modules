var DatagridMoveRow = (function($) {
	function DatagridMoveRow(gridTarget) {
		this.el = gridTarget;
		this.$el = $(this.el);
		this.rowIndex = -1;
		this.rowsCount = this.$el.datagrid('getData').rows.length;
		return this;
	}
	DatagridMoveRow.prototype = {
		getRowindex : function() {
			var selectRowIndex = this.$el.datagrid('getSelectedIndex');
			if (selectRowIndex == -1) {
				this.rowIndex = 0;
			} else {
				this.rowIndex = selectRowIndex;
			}
		},
		moveUp : function() {
			this.getRowindex();
			if (this.rowIndex == 0) {
				return false;
			}
			var i = --this.rowIndex;
			if (i > -1) {
				this.$el.datagrid('selectRow', i);
			} else {
				this.rowIndex = 0;
			}
			return false;
		},
		moveDown : function() {
			this.getRowindex();

			if (this.rowIndex == this.rowsCount - 1) {
				return false;
			}
			var i = ++this.rowIndex;
			this.$el.datagrid('selectRow', i);
		}
	}
	return DatagridMoveRow;
})(jQuery);