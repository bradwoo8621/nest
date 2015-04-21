/**
 * data tables react. if scrollY is set, width of columns must be set. width
 * automatically calculation is not support yet.
 */
var DataTable = React.createClass({
	displayName: "DataTable",
	propTypes: {
		id: React.PropTypes.string.isRequired, // id used in UI
		name: React.PropTypes.string, // property name in model, use id instead if not exists
		model: React.PropTypes.object.isRequired,
		title: React.PropTypes.string,
		
		columns: React.PropTypes.arrayOf(React.PropTypes.shape({
			title: React.PropTypes.string,
			data: React.PropTypes.string, // path of value
			render: React.PropTypes.func,
			width: React.PropTypes.number, // eg. 100 is 100px
		})),
		layout: React.PropTypes.shape({
				scrollY: React.PropTypes.number, // eg. 200 is 200px
				scrollX: React.PropTypes.bool,
				fixedLeftColumns: React.PropTypes.number,
				fixedRightColumns: React.PropTypes.number,
				addable: React.PropTypes.bool,
				removable: React.PropTypes.bool,
				editable: React.PropTypes.bool,
				indexable: React.PropTypes.bool,
				pagable: React.PropTypes.bool,
				countPerPage: React.PropTypes.number,
		}),
		editLayout: React.PropTypes.object,
		
		newClassName: React.PropTypes.string,
	},
	getDefaultProps: function() {
		return {
		};
	},
	getInitialState: function() {
		return {
			currentPageIndex: 1, // page number count from 1
			pageCount: 1, // page count default 1
			countPerPage: 2, // count per page default 10
		};
	},
	componentDidMount: function () {
		this.createComponent();
		this.getModel().addListener(this.getPropertyName(),  ["post-add", "post-remove", "post"], this.handleModelChange);
	},
	componentWillUnmount: function() {
		this.getModel().removeListener(this.getPropertyName(), ["post-add", "post-remove", "post"], this.handleModelChange);
	},
	createComponent: function() {
		var _this = this;
		$("#scrolled-body-" + this.props.id).scroll(function(e) {
			var $this = $(this);
			$("#scrolled-head-" + _this.props.id).scrollLeft($this.scrollLeft());
			$("#scrolled-left-columns-body-" + _this.props.id).scrollTop($this.scrollTop());
			$("#scrolled-right-columns-body-" + _this.props.id).scrollTop($this.scrollTop());
		});
	},
	renderHeadingButtons: function() {
		if (this.props.layout.addable) {
			return (
				<ul className="pagination pull-right">
					<li><a href="javascript:void(0);" onClick={this.addClicked}><Glyphicon glyph="plus-sign" /> Add</a></li>
				</ul>
			);
		} else {
			return null;
		}
	},
	// render operation cell
	renderOperationCell: function(column, data) {
		var editButton = column.editable ? 
			(<Button bsSize="small" bsStyle="link" onClick={this.editClicked}>
				<Glyphicon glyph="pencil" />
			</Button>) : null;
		var removeButton = column.removable ? 
			(<Button bsSize="small" bsStyle="link" onClick={this.removeClicked}>
				<Glyphicon glyph="remove" />
			</Button>) : null;
		return (<ButtonGroup className="button-in-table-cell">{editButton}{removeButton}</ButtonGroup>);
	},
	// render fixed left body rows
	renderBodyRow: function(row, rowIndex, all, leftFixed, rightFixed) {
		var indexToRender = this.getRenderMaxColumnIndex(all, leftFixed, rightFixed);
		var columnIndex = 0;
		var _this = this;
		var className = rowIndex % 2 == 0 ? "even" : "odd";
		return (<tr className={className}>{
			this.columns.map(function(column) {
				if (columnIndex >= indexToRender.min && columnIndex <= indexToRender.max) {
					// column is fixed.
					columnIndex++;
					var style = {width: column.width};
					if (!(column.visible === undefined || column.visible === true)) {
						style.display = "none";
					}
					var data;
					if (column.editable || column.removable) {
						data = _this.renderOperationCell(column, row);
					} else if (column.indexable) {
						data = rowIndex;
					} else if (row.get) {
						data = row.get(column.data);
					} else {
						data = row[column.data];
					}
					if (data != null && column.render) {
						data = column.render(data);
					}
					return (<td style={style}>{data}</td>);
				} else {
					columnIndex++;
				}
			})
		}</tr>)
	},
	computePageCount: function() {
		var data = this.getValueFromModel();
		var pageCount =  data.length == 0 ? 1 : data.length / this.state.countPerPage;
		return (Math.floor(pageCount) == pageCount) ? pageCount : (Math.floor(pageCount) + 1);
	},
	computePagination: function() {
		var minRowIndex = 0;
		var maxRowIndex = 999999;
		if (this.isPagable()) {
			this.state.countPerPage = this.props.layout.countPerPage ? 
				this.props.layout.countPerPage : this.state.countPerPage;
			this.state.pageCount = this.computePageCount();
			this.state.currentPageIndex = this.state.currentPageIndex > this.state.pageCount ? this.state.pageCount : this.state.currentPageIndex;
			minRowIndex = (this.state.currentPageIndex - 1) * this.state.countPerPage + 1;
			maxRowIndex = minRowIndex + this.state.countPerPage - 1;
		}
		return {min: minRowIndex, max: maxRowIndex};
	},
	// render body content
	renderBody: function(all, leftFixed, rightFixed) {
		var data = this.getValueFromModel();
		var rowIndex = 1;
		var _this = this;
		var range = this.computePagination();
		return (<tbody onMouseMove={this.rowMouseMove} onMouseOut={this.rowMouseOut}>
			{data.map(function(element) {
				if (rowIndex >= range.min && rowIndex <= range.max) {
					return _this.renderBodyRow(element, rowIndex++, all, leftFixed, rightFixed);
				} else {
					rowIndex++;
					return null;
				}
			})}
		</tbody>);
	},
	// render heading content
	renderHeading: function(all, leftFixed, rightFixed) {
		var indexToRender = this.getRenderMaxColumnIndex(all, leftFixed, rightFixed);
		var columnIndex = 0;
		var _this = this;
		return (<thead>
			{this.columns.map(function(column) {
				if (columnIndex >= indexToRender.min && columnIndex <= indexToRender.max) {
					// column is fixed.
					columnIndex++;
					var style = {};
					style.width = column.width;
					if (!(column.visible === undefined || column.visible === true)) {
						style.display = "none";
					}
					return (<td style={style}>{column.title}</td>);
				} else {
					columnIndex++;
				}
			})}
		</thead>);
	},
	// get max render column index. all is true, return 10000; all is false,
	// return max fixed left column index
	getRenderMaxColumnIndex: function(all, leftFixed, rightFixed) {
		var index = {};
		if (all) {
			index.min = 0;
			index.max = 10000;
		} else if (leftFixed) {
			index.min = 0;
			index.max = this.getMaxFixedLeftColumnIndex();
		} else if (rightFixed) {
			index.min = this.getMinFixedRightColumnIndex();
			index.max = 10000;
		}
		return index;
	},
	// get max fixed left column index. if no column is fixed in left, return -1
	getMaxFixedLeftColumnIndex: function() {
		return this.fixedLeftColumns - 1;
	},
	// get min fixed right column index. if no column is fixed in right, return
	// max column index.
	// eg. there are 3 columns in table, if no fixed right column, return 3. if
	// 1 fixed right column, return 2.
	getMinFixedRightColumnIndex: function() {
		return this.columns.length - this.fixedRightColumns;
	},
	// compute table style
	computeTableStyle: function() {
		var width = 0;
		if (this.props.layout.scrollX) {
			width = 0;
			// calculate width
			this.columns.forEach(function(element) {
				if (element.visible === undefined || element.visible === true) {
					width += element.width;
				}
			});
		} else {
			width = "100%";
		}
		return {width: width, maxWidth: width};
	},
	// compute fixed left columns width
	computeFixedLeftColumnsWidth: function() {
		var width = 0;
		var fixedLeftColumns = this.getMaxFixedLeftColumnIndex();
		var columnIndex = 0;
		this.columns.forEach(function(element) {
			if (columnIndex <= fixedLeftColumns && (element.visible === undefined || element.visible === true)) {
				// column is fixed.
				width += element.width;
			}
			columnIndex++;
		});
		return width;
	},
	// compute fixed right columns width
	computeFixedRightColumnsWidth: function() {
		var width = 0;
		var fixedRightColumns = this.getMinFixedRightColumnIndex();
		var columnIndex = 0;
		this.columns.forEach(function(element) {
			if (columnIndex >= fixedRightColumns && (element.visible === undefined || element.visible === true)) {
				// column is fixed
				width += element.width;
			}
			columnIndex++;
		});
		return width;
	},
	// render fixed right columns with no scroll Y
	renderFixedRightColumnsNoScrollY: function() {
		var divStyle = {width: this.computeFixedRightColumnsWidth()};
		return 	(<div className="table-fix-right" style={divStyle}>
			<table cellSpacing="0" className="datatable cell-border" style={{width: "100%"}}>
				{this.renderHeading(false, false, true)}
				{this.renderBody(false, false, true)}
			</table>
		</div>);
	},
	// render fixed right columns with scroll Y
	renderFixedRightColumnsScrollY: function() {
		var divStyle = {width: this.computeFixedRightColumnsWidth() + 1, right: "16px"};
		var bodyDivStyle = {width: "100%", overflow: "hidden"};
		if (this.props.layout.scrollX) {
			// ie8 box mode, scrollbar is not in height.
			// ie>8 or chrome, scrollbar is in height.
			bodyDivStyle.maxHeight = this.props.layout.scrollY - ((isIE() == 8) ? 0 : 17);
		}
		var bodyId = "scrolled-right-columns-body-" + this.props.id;
		return (
			<div className="table-fix-right" style={divStyle}>
				<div className="table-fix-right-head-wrapper">
					<div className="table-fix-right-top-corner" />
					<table cellSpacing="0" style={{width: "100%"}} className="datatable cell-border">
						{this.renderHeading(false, false, true)}
					</table>
				</div>
				<div id={bodyId} style={bodyDivStyle}>
					<table cellSpacing="0" className="datatable cell-border">
						{this.renderBody(false, false, true)}
					</table>
				</div>
			</div>
		);
	},
	// render fixed right columns
	renderFixedRightColumns: function() {
		if (!this.hasFixedRightColumns()) {
			return null;
		}
		if (this.props.layout.scrollY) {
			return this.renderFixedRightColumnsScrollY();
		} else {
			return this.renderFixedRightColumnsNoScrollY();
		}
	},
	hasFixedRightColumns: function() {
		return this.fixedRightColumns > 0;
	},
	// render fixed left columns with no scroll Y
	renderFixedLeftColumnsNoScrollY: function() {
		var divStyle = {width: this.computeFixedLeftColumnsWidth()};
		return 	(<div className="table-fix-left" style={divStyle}>
			<table cellSpacing="0" className="datatable cell-border" style={{width: "100%"}}>
				{this.renderHeading(false, true)}
				{this.renderBody(false, true)}
			</table>
		</div>);
	},
	// render fixed left columns with scroll Y
	renderFixedLeftColumnsScrollY: function() {
		var divStyle = {width: this.computeFixedLeftColumnsWidth() + 1};
		var bodyDivStyle = {width: "100%", overflow: "hidden"};
		if (this.props.layout.scrollX) {
			bodyDivStyle.maxHeight = this.props.layout.scrollY - ((isIE() == 8) ? 0 : 17);
		}
		var bodyId = "scrolled-left-columns-body-" + this.props.id;
		return (
			<div className="table-fix-left" style={divStyle}>
				<table cellSpacing="0" style={{width: "100%"}} className="datatable cell-border">
					{this.renderHeading(false, true)}
				</table>
				<div id={bodyId} style={bodyDivStyle}>
					<table cellSpacing="0" className="datatable cell-border">
						{this.renderBody(false, true)}
					</table>
				</div>
			</div>
		);
	},
	// render fixed left columns
	renderFixedLeftColumns: function() {
		if (!this.hasFixedLeftColumns()) {
			return null;
		}
		if (this.props.layout.scrollY) {
			return this.renderFixedLeftColumnsScrollY();
		} else {
			return this.renderFixedLeftColumnsNoScrollY();
		}
	},
	hasFixedLeftColumns: function() {
		return this.fixedLeftColumns > 0;
	},
	// render table with no scroll Y
	renderTableNoScrollY: function() {
		return 	(<div className="panel-body table-only">
			<table cellSpacing="0" className="datatable cell-border" style={this.computeTableStyle()} id={this.props.id}>
				{this.renderHeading(true)}
				{this.renderBody(true)}
			</table>
		</div>);
	},
	// render table with scroll Y
	renderTableScrollY: function() {
		var style = this.computeTableStyle();
		var headId = "scrolled-head-" + this.props.id;
		var bodyId = "scrolled-body-" + this.props.id;
		return (<div className="panel-body table-only">
			<div className="table-heading" id={headId}>
				<div className="table-heading-inner" style={style}>
					<table cellSpacing="0" className="datatable cell-border" style={style}>
						{this.renderHeading(true)}
					</table>
				</div>
			</div>
			<div className="table-body" style={{maxHeight: this.props.layout.scrollY, overflowY: "scroll"}} id={bodyId}>
				<table cellSpacing="0" className="datatable cell-border" style={style} id={this.props.id}>
					{this.renderBody(true)}
				</table>
			</div>
		</div>);
	},
	// render table
	renderTable: function() {
		if (this.props.layout.scrollY) {
			return this.renderTableScrollY();
		} else {
			return this.renderTableNoScrollY();
		}
	},
	renderNoDataReminder: function() {
		if (this.hasDataToDisplay()) {
			return null;
		} else {
			return (<div className="no-data"><span>No Data Found</span></div>);
		}
	},
	// render pagination
	renderPagination: function() {
		if (this.isPagable()) {
			return (<Pagination className="datatable-pagination" pageCount={this.state.pageCount} 
				currentPageIndex={this.state.currentPageIndex} toPage={this.toPage} />);
		} else {
			return null;
		}
	},
	renderCreateContent: function() {
		return (
			<Form model={this.state.editRow} layout={this.props.editLayout} className="modal-body" ref="editForm" />
		);
	},
	renderCreateDialog: function() {
		if (this.state.editRow && this.state.editRow != null) {
			return (<Modal bsStyle="primary" title={"Create " + this.props.title} className="datatable-edit-dialog"
				backdrop="static" onRequestHide={this.discardEditClicked} >
				{this.renderCreateContent()}
				<div className="modal-footer">
					<div className="col-md-6 btn-toolbar-left">
						<Button bsStyle="danger" onClick={this.discardEditClicked}><Glyphicon glyph="trash" /> Discard</Button>
						<Button onClick={this.resetClicked}><Glyphicon glyph="erase" /> Reset</Button>
					</div>
					<div className="col-md-6">
						<Button><Glyphicon glyph="check" /> Validate</Button>
						<Button bsStyle="primary" onClick={this.confirmClicked}><Glyphicon glyph="floppy-save" /> Confirm</Button>
					</div>
				</div>
				<ModalConfirmDialog ref="editConfirmDialog" />
			</Modal>);
		} else {
			return null;
		}
	},
	// render
	render: function() {
		// if scrollY is set, force set scrollX to true, since the table will be
		// splitted to head table and body table.
		// for make sure the cell is aligned, width of columns must be set.
		if (this.props.layout.scrollY) {
			this.props.layout.scrollX = true;
		}
		// copy from this.props.columns
		this.columns = this.props.columns.slice(0);
		this.fixedRightColumns = this.props.layout.fixedRightColumns ? this.props.layout.fixedRightColumns : 0;
		this.fixedLeftColumns = this.props.layout.fixedLeftColumns ? this.props.layout.fixedLeftColumns : 0;
		
		if (this.props.layout.editable || this.props.layout.removable) {
			var config = {
				editable: this.props.layout.editable === true ? true : false,
				removable: this.props.layout.removable === true ? true : false,
				title: "",
			};
			config.width = (config.editable ? 40 : 0) + (config.removable ? 40 : 0);
			this.columns.push(config);
			this.fixedRightColumns++;
		}
		if (this.props.layout.indexable) {
			var config = {
				indexable: true,
				width: 40,
				title: "",
			};
			this.columns.splice(0, 0, config);
			this.fixedLeftColumns++;
		}
		return (
			<div className="panel panel-default panel-bold">
				<div className="panel-heading datatable-heading">
					{this.props.title}
					{this.renderHeadingButtons()}
				</div>
				<div className="datatable-body">
					{this.renderTable()}
					{this.renderFixedLeftColumns()}
					{this.renderFixedRightColumns()}
					{this.renderNoDataReminder()}
				</div>
				{this.renderPagination()}
				{this.renderCreateDialog()}
				{this.props.layout.removable ? <ModalConfirmDialog ref="removeConfirmDialog" /> : null}
			</div>
		);
	},
	// handle model change
	handleModelChange: function(evt) {
		if (evt.type == "add") {
			this.state.currentPageIndex = this.computePageCount();
			this.computePagination();
			this.setState({editRow: null, editType: null});
		} else if (evt.type == "remove") {
			this.computePagination();
			this.forceUpdate();
		} else if (evt.type == "change") {
			this.setState({editRow: null, editType: null, editIndex: null});
		}
	},
	// jump to page by given page index
	toPage: function(pageIndex) {
		this.setState({currentPageIndex: pageIndex});
	},
	// get operating row index, in current page
	getOperatingRowIndex: function(evt) {
		var tr = $(evt.target).closest("tr");
		var rows = tr.parent().children("tr");
		return rows.index(tr);
	},
	// get operating row index, in whole page
	getAbsoluteOperatingRowIndex: function(evt) {
		var index = this.getOperatingRowIndex(evt);
		if (this.isPagable()) {
			return this.state.countPerPage * (this.state.currentPageIndex - 1) + index;
		} else {
			// no pagination
			return index;
		}
	},
	rowMouseMove: function(evt) {
		var index = this.getOperatingRowIndex(evt);
		var selector = "tbody>tr:nth-child(" + (index + 1) + ")";
		$("#scrolled-body-" + this.props.id).find(selector).addClass("hover");
		$("#scrolled-left-columns-body-" + this.props.id).find(selector).addClass("hover");
		$("#scrolled-right-columns-body-" + this.props.id).find(selector).addClass("hover");
	},
	rowMouseOut: function(evt) {
		var index = this.getOperatingRowIndex(evt);
		var selector = "tbody>tr:nth-child(" + (index + 1) + ")";
		$("#scrolled-body-" + this.props.id).find(selector).removeClass("hover");
		$("#scrolled-left-columns-body-" + this.props.id).find(selector).removeClass("hover");
		$("#scrolled-right-columns-body-" + this.props.id).find(selector).removeClass("hover");
	},
	// on add button clicked
	addClicked: function() {
		this.setState({editRow: ModelUtil.create(this.props.editLayout), editType: "add"});
	},
	// on edit button clicked
	editClicked: function(evt) {
		var editIndex = this.getAbsoluteOperatingRowIndex(evt);
		var editRow = this.getValueFromModel()[editIndex];
		this.setState({editRow: new ModelProxy(editRow), editType: "edit", editIndex: editIndex});
	},
	// on remove button clicked
	removeClicked: function(evt) {
		this.refs.removeConfirmDialog.show("Delete data?",
		 	["Are you sure you want to delete data?", "It cannot be recovered."],
		 	this.removeRow, this.getAbsoluteOperatingRowIndex(evt));
	},
	// remove row
	removeRow: function(selectedRowIndex) {
		this.refs.removeConfirmDialog.hide();
		this.getModel().remove(this.getPropertyName(), selectedRowIndex);
	},
	// discard editing/creating clicked
	discardEditClicked: function() {
		this.refs.editConfirmDialog.show("Discard data?",
		 	["Are you sure you want to discard the editing data?", "All data will be lost and cannot be recovered."],
		 	this.discard);
	},
	// discard
	discard: function() {
		// hide the confirm dialog
		this.refs.editConfirmDialog.hide();
		this.setState({editRow: null});
	},
	// reset editing/creating clicked
	resetClicked: function() {
		this.refs.editConfirmDialog.show("Reset data?",
		 	["Are you sure you want to reset the editing data?", "All updated data will be lost and cannot be recovered."],
		 	this.reset);
	},
	// reset
	reset: function() {
		// reset model
		this.state.editRow.reset();
		// invoke update force
		this.refs.editForm.forceUpdate();
		// hide the confirm dialog
		this.refs.editConfirmDialog.hide();
	},
	confirmClicked: function() {
		if (this.state.editType == "add") {
			this.addItemIntoModel(this.state.editRow.getDataModel());
		} else if (this.state.editType == "edit") {
			this.updateItemIntoModel(this.state.editRow.getDataModel(), this.state.editIndex);
		}
	},
	getComponent: function() {
		return $("#" + this.props.id);
	},
	isPagable: function() {
		return this.props.layout.pagable === true ? true : false;
	},
	hasDataToDisplay: function() {
		var data = this.getValueFromModel();
		return data != null && data.length > 0;
	},
	// get model
	getModel: function() {
		return this.props.model;
	},
	// get property name
	getPropertyName: function() {
		return this.props.name ? this.props.name : this.props.id;
	},
	// get value from model
	getValueFromModel: function() {
		return this.getModel().get(this.getPropertyName());
	},
	// add item into model
	addItemIntoModel: function(item) {
		this.getModel().add(this.getPropertyName(), item);
	},
	// update item into model
	updateItemIntoModel: function(item, index) {
		this.getModel().update(this.getPropertyName(), item, index);
	},
});
